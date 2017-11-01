/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.mb;

import br.com.garimpeiro.dao.EnderecoDao;
import br.com.garimpeiro.dao.FeedBackDao;
import br.com.garimpeiro.dao.ProfileDao;
import br.com.garimpeiro.dao.ServicoPrestadorDao;
import br.com.garimpeiro.entities.Endereco;
import br.com.garimpeiro.entities.Feedback;
import br.com.garimpeiro.entities.Profile;
import br.com.garimpeiro.entities.ServicoPrestador;
import br.com.garimpeiro.util.Constants;
import br.com.garimpeiro.util.FacesMessageUtil;
import br.com.garimpeiro.util.MD5;
import br.com.garimpeiro.util.RestClient;
import java.io.File;
import java.io.Serializable;
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
import org.primefaces.event.FileUploadEvent;

/**
 * Managed Bean utilizado para gerenciar todas as funcionalidades referentes ao
 * xhml Profile do Garimpeiro.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@ManagedBean(name = "profile")
@ViewScoped
public class GarimpeiroProfile implements Serializable {

    private String texto;
    private String cep;
    private String senhaAntiga;
    private String senhaNova;
    private String senhaNovaConfirmacao;
    private boolean trocarSenha;
    private boolean admin;
    private boolean showUsers;
    private boolean feedbacks;
    private Profile usuario;
    private List<Profile> profiles;
    private Endereco endereco;
    private List<String> ufs;
    private List<Feedback> feedList;

    @ManagedProperty(value = "#{user}")
    private GarimpeiroUsuario user;

    @PostConstruct
    public void carregar() {

        try {
            if (user.getProfile() != null && usuario == null) {

                Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

                String ADM = "prestador@garimpeiroweb.com.br";

                if (StringUtils.equals(user.getProfile().getEmail(), ADM)) {
                    admin = true;
                    EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                    showUsers = Boolean.valueOf(parameterMap.get("showUsers"));
                    if (showUsers) {
                        ProfileDao pro = new ProfileDao(factory);
                        profiles = pro.findAll();
                    }
                    feedbacks = Boolean.valueOf(parameterMap.get("feedbacks"));
                    if (feedbacks) {
                        FeedBackDao feeDao = new FeedBackDao(factory);
                        feedList = feeDao.findAll();
                        feedbacks = true;
                    }
                }

                usuario = user.getProfile();
                trocarSenha = Boolean.valueOf(parameterMap.get("trocarSenha"));

                if (trocarSenha) {
                    RequestContext.getCurrentInstance().update("centro");

                } else {
                    EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                    EnderecoDao endDao = new EnderecoDao(factory);
                    endereco = endDao.findByProfile(usuario);
                    if (endereco != null) {
                        cep = endereco.getCep();
                    }
                    ServicoPrestadorDao srvDao = new ServicoPrestadorDao(factory);
                    List<ServicoPrestador> servicos = srvDao.findByProfile(usuario);
                    if (servicos != null && servicos.size() > 0) {
                        texto = servicos.get(0).getServico().getNome();
                    }
                    factory.close();
                }
            }
        } catch (Exception e) {
            System.err.println("ERRO ao carregar profile " + e);
        }
    }

    public void upload(FileUploadEvent event) {
        try {
            if (event.getFile() != null && user != null && user.getProfile() != null) {
                File file = new File(Constants.TMP_DIR + event.getFile().getFileName());
                FileUtils.copyInputStreamToFile(event.getFile().getInputstream(), file);
                String url = RestClient.enviarArquivo(file, file.getName());
                file.delete();
                if (StringUtils.isNotBlank(url)) {
                    user.getProfile().setUrl(url);
                    EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                    ProfileDao dao = new ProfileDao(factory);
                    dao.update(user.getProfile());
                    factory.close();
                    RequestContext.getCurrentInstance().update("body");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar foto do perfil: " + e);
        }
    }

    public void senha() {
        if (StringUtils.equalsIgnoreCase(MD5.gerar(senhaAntiga), user.getProfile().getPassword()) && StringUtils.equalsIgnoreCase(senhaNova, senhaNovaConfirmacao)) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
            ProfileDao proDao = new ProfileDao(factory);
            user.getProfile().setPassword(MD5.gerar(senhaNova));
            proDao.update(user.getProfile());
            FacesMessageUtil.info(Constants.SENHA, Constants.SENHA);
            factory.close();
        } else {
            FacesMessageUtil.erro(Constants.SENHA_ERRO, Constants.SENHA_ERRO);
        }
    }

    public void atualizar() {
        try {
            if (usuario != null && StringUtils.isNotBlank(usuario.getNome())) {
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                ProfileDao proDao = new ProfileDao(factory);
                proDao.update(usuario);
                if (endereco != null && StringUtils.isNotBlank(endereco.getLogradouro())) {
                    endereco.setProfile(usuario);
                    EnderecoDao endDao = new EnderecoDao(factory);
                    if (endereco.getId() == null || endereco.getId() == 0) {
                        endDao.create(endereco);
                    } else {
                        endDao.update(endereco);
                    }
                }
                factory.close();
                FacesMessageUtil.info("Atualizado com sucesso", "Atualizado com sucesso");
            } else {
                FacesMessageUtil.erro("Nada a ser feito", "Nada a ser feito");
            }
        } catch (Exception e) {
            System.err.println("ERRO ao atualizar profile " + e);
        }
    }

    public void keyEventCep() {
        if (StringUtils.isNotBlank(cep) && cep.replaceAll(Constants.REGEX, "").length() == 8) {
            Endereco end = RestClient.findCep(cep.replaceAll(Constants.REGEX, ""));
            if (end != null) {
                endereco = end;
                cep = endereco.getCep();
            }
        }
    }

    public Profile getUsuario() {
        if (usuario == null) {
            usuario = new Profile();
        }
        return usuario;
    }

    public void setUsuario(Profile usuario) {
        this.usuario = usuario;
    }

    public List<String> getUfs() {
        if (ufs == null) {
            ufs = Constants.ufs();
        }
        return ufs;
    }

    public void setUfs(List<String> ufs) {
        this.ufs = ufs;
    }

    public GarimpeiroUsuario getUser() {
        return user;
    }

    public void setUser(GarimpeiroUsuario user) {
        this.user = user;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public boolean isTrocarSenha() {
        return trocarSenha;
    }

    public void setTrocarSenha(boolean trocarSenha) {
        this.trocarSenha = trocarSenha;
    }

    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }

    public String getSenhaNovaConfirmacao() {
        return senhaNovaConfirmacao;
    }

    public void setSenhaNovaConfirmacao(String senhaNovaConfirmacao) {
        this.senhaNovaConfirmacao = senhaNovaConfirmacao;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isShowUsers() {
        return showUsers;
    }

    public void setShowUsers(boolean showUsers) {
        this.showUsers = showUsers;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public boolean isFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(boolean feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<Feedback> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<Feedback> feedList) {
        this.feedList = feedList;
    }
}
