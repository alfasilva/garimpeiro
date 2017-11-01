/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.mb;

import br.com.garimpeiro.dao.NegociacaoDao;
import br.com.garimpeiro.dao.ServicoDao;
import br.com.garimpeiro.dao.ServicoPrestadorDao;
import br.com.garimpeiro.dao.ServicoQualificacaoDao;
import br.com.garimpeiro.entities.Categoria;
import br.com.garimpeiro.entities.Servico;
import br.com.garimpeiro.entities.ServicoPrestador;
import br.com.garimpeiro.entities.ServicoQualificacao;
import br.com.garimpeiro.util.Mapa;
import br.com.garimpeiro.vo.RetornoPesquisa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

/**
 * Managed Bean utilizado para gerenciar todas as funcionalidades referentes ao
 * xhml principal do Garimpeiro.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@ManagedBean(name = "pesquisa")
public class GarimpeiroPesquisa implements Serializable {

    private String palavra;
    private List<RetornoPesquisa> resultado;

    @ManagedProperty(value = "#{user}")
    private GarimpeiroUsuario user;

    /**
     * Metodo buscar as informacoes na base de dados.
     *
     */
    public void buscar() {
        try {
            if (StringUtils.isNotBlank(palavra)) {
                Servico srvObj = Mapa.findServico(palavra);
                Categoria catObj = Mapa.findCategoria(palavra);
                resultado = new ArrayList<RetornoPesquisa>();
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");

                if (srvObj != null && srvObj.getAtivo()) {

                    ServicoQualificacaoDao qualificacaoDao = new ServicoQualificacaoDao(factory);
                    ServicoPrestadorDao servPresDao = new ServicoPrestadorDao(factory);
                    for (ServicoPrestador s : servPresDao.findByServico(srvObj)) {
                        Integer bom = 0;
                        Integer ruim = 0;
                        for (ServicoQualificacao sv : qualificacaoDao.findAllByServicoPrestador(s)) {
                            if (sv.getBom()) {
                                bom++;
                            } else {
                                ruim++;
                            }
                        }
                        RetornoPesquisa resposta = new RetornoPesquisa(s);
                        resposta.setBom(bom);
                        resposta.setRuim(ruim);
                        NegociacaoDao negDao = new NegociacaoDao(factory);
                        resposta.setNegociacao(negDao.findByServicoPrestador(s));
                        resultado.add(resposta);
                    }
                } else if (catObj != null) {
                    ServicoQualificacaoDao qualificacaoDao = new ServicoQualificacaoDao(factory);
                    ServicoPrestadorDao servPresDao = new ServicoPrestadorDao(factory);
                    ServicoDao srvDao = new ServicoDao(factory);
                    for (Servico s : srvDao.findByCategoria(catObj)) {
                        for (ServicoPrestador sp : servPresDao.findByServico(s)) {
                            Integer bom = 0;
                            Integer ruim = 0;
                            for (ServicoQualificacao sv : qualificacaoDao.findAllByServicoPrestador(sp)) {
                                if (sv.getBom()) {
                                    bom++;
                                } else {
                                    ruim++;
                                }
                            }
                            RetornoPesquisa resposta = new RetornoPesquisa(sp);
                            resposta.setBom(bom);
                            resposta.setRuim(ruim);
                            NegociacaoDao negDao = new NegociacaoDao(factory);
                            resposta.setNegociacao(negDao.findByServicoPrestador(sp));
                            resultado.add(resposta);
                        }
                    }
                }

                if (resultado == null || resultado.isEmpty()) {
                    ServicoQualificacaoDao qualificacaoDao = new ServicoQualificacaoDao(factory);
                    ServicoPrestadorDao servPresDao = new ServicoPrestadorDao(factory);
                    ServicoPrestador s = servPresDao.findById(8L);
                    Integer bom = 0;
                    Integer ruim = 0;
                    for (ServicoQualificacao sv : qualificacaoDao.findAllByServicoPrestador(s)) {
                        if (sv.getBom()) {
                            bom++;
                        } else {
                            ruim++;
                        }
                    }
                    RetornoPesquisa resposta = new RetornoPesquisa(s);
                    resposta.setBom(bom);
                    resposta.setRuim(ruim);
                    NegociacaoDao negDao = new NegociacaoDao(factory);
                    resposta.setNegociacao(negDao.findByServicoPrestador(s));
                    resultado.add(resposta);
                }

                factory.close();
            }
        } catch (Exception e) {
            System.err.println("ERRO Pesquisa: " + e);
        }
        RequestContext.getCurrentInstance().update("container");
        RequestContext.getCurrentInstance().update("footer");
    }

    public List<String> autoComplete(String texto) {
        List<String> a = new ArrayList<String>();
        if (StringUtils.isNotBlank(texto)) {
            Map<Integer, Servico> servicos = Mapa.servicos();
            Map<Integer, Categoria> categorias = Mapa.categorias();
            if (texto.length() > 2) {
                for (Map.Entry<Integer, Servico> entry : servicos.entrySet()) {
                    if (entry.getValue().getNome().toLowerCase().contains(texto.toLowerCase())) {
                        a.add(entry.getValue().getNome());
                    }
                }
                for (Map.Entry<Integer, Categoria> entry : categorias.entrySet()) {
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
                for (Map.Entry<Integer, Categoria> entry : categorias.entrySet()) {
                    if (entry.getValue().getNome().toLowerCase().startsWith(texto.toLowerCase())) {
                        a.add(entry.getValue().getNome());
                    }
                }
            }
        }
        return a;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public List<RetornoPesquisa> getResultado() {
        return resultado;
    }

    public void setResultado(List<RetornoPesquisa> resultado) {
        this.resultado = resultado;
    }

    public GarimpeiroUsuario getUser() {
        return user;
    }

    public void setUser(GarimpeiroUsuario user) {
        this.user = user;
    }
}
