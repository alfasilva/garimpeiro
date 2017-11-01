/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.mb;

import br.com.garimpeiro.dao.ProfileDao;
import br.com.garimpeiro.entities.Profile;
import br.com.garimpeiro.util.FacesMessageUtil;
import br.com.garimpeiro.util.MD5;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

/**
 * Managed Bean utilizado para gerenciar todas as funcionalidades referentes ao
 * ao usuário logado no xhml principal do Garimpeiro.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@ManagedBean(name = "user")
@SessionScoped
public class GarimpeiroUsuario implements Serializable {

    private String nome;
    private String email;
    private String senha;
    private Profile profile;
    private Integer mensages;

    public void logar() {
        if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(senha)) {
            try {
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                ProfileDao profileDao = new ProfileDao(factory);
                profile = profileDao.authenticar(email, MD5.gerar(senha));
                if (profile != null) {
                    nome = defineNomeUsuario(profile.getNome());                    
                    update();
                }
                factory.close();
            } catch (Exception e) {
                limparCamposLogin();
                FacesMessageUtil.erro("Usuário ou Senha inválidos!", "Usuário ou Senha inválidos!");
                RequestContext.getCurrentInstance().update("formLogin");
                System.err.println("Erro ao autenticar Usuario " + e.getMessage());                
            }
            limparCamposLogin();
        }
    }

    public void popup() {
        System.out.println("POPUP");
    }

    protected void limparCamposLogin() {
        nome = "";
        email = "";
        senha = "";
    }

    public void desLogar() {
        try {
            nome = "";
            email = "";
            senha = "";
            profile = null;
            update();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (Exception ex) {
            System.out.println("Erro ao deslogar: " + profile.getNome()+" "+ex);
        }
    }

    /**
     * Metodo Utilizado para dimninuir o nome do Usario na tela
     *
     * @param n
     * @return
     */
    protected String defineNomeUsuario(String n) {
        if (n.split(" ").length > 1) {
            String[] t = n.split(" ");
            return t[0];
        }
        return n;
    }

    private void update() {
        RequestContext.getCurrentInstance().update("container");
        RequestContext.getCurrentInstance().update("menuSuperiorGroup");
        RequestContext.getCurrentInstance().update("naoLogado");
        RequestContext.getCurrentInstance().update("footer");
        RequestContext.getCurrentInstance().update("loginGroup");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Profile getProfile() {
        return profile;
    }

    public Integer getMensages() {
        return mensages;
    }

    public String getNome() {
        return nome;
    }
}
