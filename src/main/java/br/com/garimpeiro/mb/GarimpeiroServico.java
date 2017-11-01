/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.mb;

import br.com.garimpeiro.dao.ProfileDao;
import br.com.garimpeiro.dao.ServicoPrestadorDao;
import br.com.garimpeiro.dao.ServicoQualificacaoDao;
import br.com.garimpeiro.entities.Profile;
import br.com.garimpeiro.entities.Servico;
import br.com.garimpeiro.entities.ServicoPrestador;
import br.com.garimpeiro.entities.ServicoQualificacao;
import br.com.garimpeiro.util.Constants;
import br.com.garimpeiro.util.FacesMessageUtil;
import br.com.garimpeiro.util.Mapa;
import br.com.garimpeiro.util.RestClient;
import br.com.garimpeiro.vo.RetornoServico;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 * Managed Bean utilizado para gerenciar todas as funcionalidades referentes ao
 * xhml Servico do Garimpeiro.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@ManagedBean(name = "servico")
@ViewScoped
public class GarimpeiroServico implements Serializable {

    private Long uid;
    private Long bs;
    private String texto;
    private String url;
    private String descricao;
    private long valor;
    private Profile prestador;
    private Servico servico;
    private UploadedFile file;
    private ServicoPrestador preServico;
    private List<RetornoServico> retorno;

    @ManagedProperty(value = "#{user}")
    private GarimpeiroUsuario user;

    @PostConstruct
    public void carregar() {
        System.out.println("Invocando PostConstruct do Servico");
        try {
            Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            if (user.getProfile() != null && StringUtils.isNotBlank(parameterMap.get("uid")) && StringUtils.isNotBlank(parameterMap.get("business"))) {
                uid = Long.parseLong(parameterMap.get("uid"));
                bs = Long.parseLong(parameterMap.get("business"));
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                ProfileDao proDao = new ProfileDao(factory);
                ServicoPrestadorDao srvPresDao = new ServicoPrestadorDao(factory);
                prestador = proDao.find(uid);
                preServico = srvPresDao.find(bs);
                if (prestador != null && preServico != null && StringUtils.isNotBlank(parameterMap.get("vote"))) {
                    ServicoQualificacaoDao sqDao = new ServicoQualificacaoDao(factory);
                    sqDao.create(new ServicoQualificacao(Boolean.parseBoolean(parameterMap.get("vote")), descricao, new Date(), user.getProfile(), prestador, preServico));
                    List<RetornoServico> lista = new ArrayList<RetornoServico>();
                    Integer bom = 0;
                    Integer ruim = 0;
                    for (ServicoQualificacao sv : sqDao.findAllByServicoPrestador(preServico)) {
                        if (sv.getBom()) {
                            bom++;
                        } else {
                            ruim++;
                        }
                    }
                    RetornoServico resposta = new RetornoServico(preServico);
                    resposta.setBom(bom);
                    resposta.setRuim(ruim);
                    lista.add(resposta);
                    retorno = lista;
                } else {
                    retorno = carregarServicos(prestador);
                }                
                factory.close();
            } else if (user.getProfile() != null) {
                prestador = null;
                retorno = carregarServicos(user.getProfile());
            }
            RequestContext.getCurrentInstance().update("container");
        } catch (Exception e) {
            System.err.println("Erro ao carregar servico " + e);
        }
    }

    protected List<RetornoServico> carregarServicos(Profile u, Servico srv) {
        List<RetornoServico> lista = new ArrayList<RetornoServico>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
        ServicoQualificacaoDao qualificacaoDao = new ServicoQualificacaoDao(factory);
        ServicoPrestadorDao servPresDao = new ServicoPrestadorDao(factory);
        for (ServicoPrestador s : servPresDao.findByServico(srv)) {
            Integer bom = 0;
            Integer ruim = 0;
            for (ServicoQualificacao sv : qualificacaoDao.findAllByServicoPrestador(s)) {
                if (sv.getBom()) {
                    bom++;
                } else {
                    ruim++;
                }
            }
            RetornoServico resposta = new RetornoServico(s);
            resposta.setBom(bom);
            resposta.setRuim(ruim);
            lista.add(resposta);
        }
        factory.close();
        return lista;
    }

    /**
     * Carrefa Lista de Servicos de um determinado prestador
     *
     * @param p
     * @return
     */
    protected List<RetornoServico> carregarServicos(Profile p) {

        List<RetornoServico> lista = new ArrayList<RetornoServico>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
        ServicoQualificacaoDao qualificacaoDao = new ServicoQualificacaoDao(factory);
        ServicoPrestadorDao servPresDao = new ServicoPrestadorDao(factory);
        for (ServicoPrestador s : servPresDao.findByProfile(p)) {
            Integer bom = 0;
            Integer ruim = 0;
            for (ServicoQualificacao sv : qualificacaoDao.findAllByServicoPrestador(s)) {
                if (sv.getBom()) {
                    bom++;
                } else {
                    ruim++;
                }
            }
            RetornoServico resposta = new RetornoServico(s);
            resposta.setBom(bom);
            resposta.setRuim(ruim);
            lista.add(resposta);
        }
        return lista;
    }

    public void cadastrar() {
        try {
            if (file != null && StringUtils.isNotBlank(file.getFileName()) && user != null && user.getProfile() != null) {
                File targetFile = new File("/tmp/" + file.getFileName());
                FileUtils.copyInputStreamToFile(file.getInputstream(), targetFile);
                Servico s = Mapa.findServico(texto);
                preServico = new ServicoPrestador(valor, new Date(), descricao, url, user.getProfile(), s);
                uploadFotoServico(targetFile);
                retorno.add(new RetornoServico(0, 0, preServico));
                texto = "";
                url = "";
                descricao = "";
                valor = 0;
                FacesMessageUtil.info("Cadastrado com Sucesso", "Cadastrado com Sucesso");
                RequestContext.getCurrentInstance().update("dataScrollerServicos");
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                ServicoPrestadorDao srvPresDao = new ServicoPrestadorDao(factory);                
                srvPresDao.create(preServico);
                factory.close();
            } else {
                FacesMessageUtil.erro("Foto Obrigatoria", "Foto Obrigatoria");
            }
        } catch (Exception ex) {
            FacesMessageUtil.erro("Erro cadastrar Serviço!", "Erro cadastrar Serviço!");
            System.out.println("Erro ao Cadastrar Servico: " + ex);
        }
    }

    protected void uploadFotoServico(File file) {
        try {
            if (file != null) {
                url = RestClient.enviarArquivo(file, file.getName());
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                ServicoPrestadorDao srv = new ServicoPrestadorDao(factory);
                preServico.setUrl(url);
                srv.create(preServico);
                factory.close();
                file.delete();
            }
        } catch (Exception e) {
            FacesMessageUtil.erro(Constants.ERRO_ATUALIZACAO, Constants.ERRO_ATUALIZACAO);
            System.err.println("Erro ao fazer Upload do arquivo " + e.getMessage());
        }
    }

    public List<String> autoComplete(String texto) {
        List<String> a = new ArrayList<String>();
        if (StringUtils.isNotBlank(texto)) {
            Map<Integer, Servico> servicos = Mapa.servicos();
            if (texto.length() > 2) {
                for (Map.Entry<Integer, Servico> entry : servicos.entrySet()) {
                    if (entry.getValue().getNome().toLowerCase().contains(texto.toLowerCase())) {
                        a.add(entry.getValue().getNome());
                    }
                }
            } else {
                for (Map.Entry<Integer, Servico> entry : servicos.entrySet()) {
                    if (entry.getValue().getNome().toLowerCase().startsWith(texto.toLowerCase())) {
                        a.add(entry.getValue().getNome());
                    }
                }
            }
        }
        return a;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getBs() {
        return bs;
    }

    public void setBs(Long bs) {
        this.bs = bs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public ServicoPrestador getPreServico() {
        return preServico;
    }

    public void setPreServico(ServicoPrestador preServico) {
        this.preServico = preServico;
    }

    public GarimpeiroUsuario getUser() {
        return user;
    }

    public void setUser(GarimpeiroUsuario user) {
        this.user = user;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Profile getPrestador() {
        return prestador;
    }

    public void setPrestador(Profile prestador) {
        this.prestador = prestador;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public List<RetornoServico> getRetorno() {
        if (retorno == null) {
            retorno = new ArrayList<RetornoServico>();
        }
        return retorno;
    }

    public void setRetorno(List<RetornoServico> retorno) {
        this.retorno = retorno;
    }
}
