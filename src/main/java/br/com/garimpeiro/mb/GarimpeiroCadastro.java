/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.mb;

import br.com.garimpeiro.dao.CategoriaDao;
import br.com.garimpeiro.dao.CategoriaPrestadorDao;
import br.com.garimpeiro.dao.ConfirmacaoCadastroDao;
import br.com.garimpeiro.dao.EnderecoDao;
import br.com.garimpeiro.dao.FeedBackDao;
import br.com.garimpeiro.dao.ProfileDao;
import br.com.garimpeiro.dao.ServicoDao;
import br.com.garimpeiro.dao.ServicoPrestadorDao;
import br.com.garimpeiro.entities.Categoria;
import br.com.garimpeiro.entities.CategoriaPrestador;
import br.com.garimpeiro.entities.ConfirmacaoCadastro;
import br.com.garimpeiro.entities.Endereco;
import br.com.garimpeiro.entities.Feedback;
import br.com.garimpeiro.entities.Profile;
import br.com.garimpeiro.entities.Servico;
import br.com.garimpeiro.entities.ServicoPrestador;
import br.com.garimpeiro.util.Constants;
import br.com.garimpeiro.util.FacesMessageUtil;
import br.com.garimpeiro.util.MD5;
import br.com.garimpeiro.util.MailClient;
import br.com.garimpeiro.util.Mapa;
import br.com.garimpeiro.util.RestClient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.commons.lang3.StringUtils;

/**
 * Managed Bean utilizado para gerenciar todas as funcionalidades referentes ao
 * xhml principal do Garimpeiro.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@ManagedBean(name = "cadastro")
@ViewScoped
public final class GarimpeiroCadastro implements Serializable {

    private String texto;
    private String mail;
    private String tipo;
    private String cep;
    private String uf;
    private String password;
    private String textFeedback;
    private boolean showFeedback;
    private Integer idCategoria;
    private boolean primeiro;
    private boolean cliente;
    private boolean prestador;
    private boolean fim;
    private Profile profile;
    private Endereco endereco;
    private List<Categoria> categorias;
    private List<String> ufs;

    @ManagedProperty(value = "#{user}")
    private GarimpeiroUsuario user;

    @PostConstruct
    public void carregar() {
        try {
            primeiro = true;
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
            CategoriaDao dao = new CategoriaDao(factory);
            categorias = dao.findAll();
            factory.close();
        } catch (Exception e) {
            System.err.println("Erro ao carregar cadastro: " + e);
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

    /**
     * Metodo utilizado para salvar o feedback dos usuarios .
     *
     */
    public void feedback() {
        if (StringUtils.isNotBlank(textFeedback) && user.getProfile() != null) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
            FeedBackDao feed = new FeedBackDao(factory);
            feed.create(new Feedback(textFeedback, new Date(), user.getProfile()));
            FacesMessageUtil.info(Constants.OBR_OPINIAO, Constants.OBR_OPINIAO);
            showFeedback = true;
            if (factory.isOpen()) {
                factory.close();
            }
        }
    }

    /**
     * Metodo utilizado para salvar um novo Profiles.
     *
     */
    public void mudarTipo() {

        profile = null;
        clear();

        if (StringUtils.equalsIgnoreCase(tipo, "1")) {
            cliente = true;
        } else if (StringUtils.equalsIgnoreCase(tipo, "2")) {
            cliente = true;
            prestador = true;
        }
    }

    /**
     * Metodo utilizado para salvar um novo Profiles.
     *
     */
    public void savePrestador() {

        try {

            profile.setPassword(MD5.gerar(profile.getPassword()));

            if (StringUtils.equals(profile.getPassword(), MD5.gerar(password))) {

                profile.setTipo(new Integer(tipo));
                profile.setData(new Date());
                profile.setUrl(Constants.PRIMEIRA_IMG_URL);

                if (profile.getTipo().compareTo(1) == 0) {
                    tipo = "";
                    clear();
                    profile = savarProfile(profile);
                    fim = true;
                } else if (profile.getTipo().compareTo(2) == 0) {
                    if (profile != null && StringUtils.isNotBlank(profile.getPassword()) && endereco != null && StringUtils.isNotBlank(endereco.getCep())) {
                        Profile prest = savarProfile(profile);
                        if (prest != null) {
                            EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                            EnderecoDao endDao = new EnderecoDao(factory);
                            endereco.setProfile(prest);
                            endDao.create(endereco);
                            ServicoDao servicoDao = new ServicoDao(factory);
                            Servico s = servicoDao.findByNome(texto);
                            CategoriaPrestadorDao catPresDao = new CategoriaPrestadorDao(factory);
                            catPresDao.create(new CategoriaPrestador(s.getCategoria(), prest));
                            ServicoPrestadorDao srvPrestDao = new ServicoPrestadorDao(factory);
                            Date dateIni = new Date();
                            Date dateFim = new Date(dateIni.getTime() + 99999999);
                            srvPrestDao.create(new ServicoPrestador(0, dateIni, dateFim, Constants.TEXTO_SERVICO, Constants.IMG_SERVICO, prest, s));
                            clear();
                            profile = prest;
                            fim = true;
                            FacesMessageUtil.info(Constants.CAD_SUCESSO, null);
                            if (factory.isOpen()) {
                                factory.close();
                            }
                        }
                        clear();
                    }
                }
            } else {
                FacesMessageUtil.erro("Senhas não são iguais.", null);
            }
        } catch (Exception e) {
            System.err.println("Erro ao Salvar o Prestador: " + e.getMessage());
            clear();
        }
    }

    /**
     * Metodo utilizado para salvar um novo Profiles Cliente con informações
     * minimas.
     *
     * @param p
     * @return
     */
    protected Profile savarProfile(Profile p) {
        Profile ret = null;
        if (p != null && StringUtils.isNotBlank(p.getEmail())) {
            try {
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                ProfileDao dao = new ProfileDao(factory);
                ret = dao.findByEmail(p.getEmail());
                if (ret == null) {
                    ret = dao.create(p);
                    confirmarCadastro(gerarToken(), ret);
                } else {
                    FacesMessageUtil.erro(Constants.MAIL_JA_CADASTRADO, null);
                }
                if (factory.isOpen()) {
                    factory.close();
                }
            } catch (Exception e) {
                FacesMessageUtil.erro(Constants.ERRO_BASE, null);
                System.err.println("Erro ao salvar dados do porfile : " + e.getMessage());
            }
        }
        return ret;
    }

    protected void clear() {
        texto = "";
        mail = "";
        cep = "";
        uf = "";
        password = "";
        idCategoria = 0;
        primeiro = false;
        cliente = false;
        prestador = false;
        fim = false;
        prestador = false;
        endereco = null;
        ufs = null;
    }

    /**
     * Gera confirmação do cadastra e envia e-mail.
     *
     * @param token
     * @param p
     */
    protected void confirmarCadastro(String token, Profile p) {

        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
            ConfirmacaoCadastroDao conFirCadDao = new ConfirmacaoCadastroDao(factory);
            ConfirmacaoCadastro conf = conFirCadDao.findByProfile(p);
            if (conf != null) {
                MailClient.enviarBoasVindas(p.getNome(), p.getEmail(), conf.getToken(), Constants.IMG_BOAS_VINDAS);
            } else {
                ConfirmacaoCadastro c = conFirCadDao.create(new ConfirmacaoCadastro(token, p));
                MailClient.enviarBoasVindas(p.getNome(), p.getEmail(), c.getToken(), Constants.IMG_BOAS_VINDAS);
            }
            if (factory.isOpen()) {
                factory.close();
            }
        } catch (Exception e) {
            FacesMessageUtil.erro(Constants.CAD_ERRO, null);
            System.err.println("ERRO ao cadastrar profile " + e);
        }

    }

    /**
     * GEra token para confirmacao do cadastro
     *
     * @return
     */
    protected String gerarToken() {
        return MD5.gerar("T" + System.currentTimeMillis() + "M");
    }

    public void keyEventCep() {
        if (StringUtils.isNotBlank(cep) && cep.replaceAll(Constants.REGEX, "").length() == 8) {
            Endereco end = RestClient.findCep(cep.replaceAll(Constants.REGEX, ""));
            if (end != null) {
                endereco = end;
                uf = end.getUf();
            }
        }
    }

    public Profile getProfile() {
        if (profile == null) {
            profile = new Profile();
        }
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isPrimeiro() {
        return primeiro;
    }

    public void setPrimeiro(boolean primeiro) {
        this.primeiro = primeiro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isCliente() {
        return cliente;
    }

    public void setCliente(boolean cliente) {
        this.cliente = cliente;
    }

    public boolean isPrestador() {
        return prestador;
    }

    public void setPrestador(boolean prestador) {
        this.prestador = prestador;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();
        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public List<String> getUfs() {
        if (ufs == null) {
            ufs = Constants.ufs();
        }
        return ufs;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public boolean isFim() {
        return fim;
    }

    public void setFim(boolean fim) {
        this.fim = fim;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTextFeedback() {
        return textFeedback;
    }

    public void setTextFeedback(String textFeedback) {
        this.textFeedback = textFeedback;
    }

    public boolean isShowFeedback() {
        return showFeedback;
    }

    public void setShowFeedback(boolean showFeedback) {
        this.showFeedback = showFeedback;
    }

    public GarimpeiroUsuario getUser() {
        return user;
    }

    public void setUser(GarimpeiroUsuario user) {
        this.user = user;
    }
}
