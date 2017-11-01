/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.mb;

import br.com.garimpeiro.dao.ProfileDao;
import br.com.garimpeiro.entities.Profile;
import br.com.garimpeiro.util.Constants;
import br.com.garimpeiro.util.FacesMessageUtil;
import br.com.garimpeiro.util.MD5;
import br.com.garimpeiro.util.MailClient;
import java.io.Serializable;
import java.util.Map;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.context.RequestContext;

/**
 * Managed Bean utilizado para gerenciar todas as funcionalidades referentes ao
 * xhml principal do Garimpeiro.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@ManagedBean(name = "faleConosco")
@ViewScoped
public final class GarimpeiroNovaSenha implements Serializable {

    private boolean novaSenha;
    private String email;

    @PostConstruct
    public void carregar() {
        Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        boolean ns = Boolean.valueOf(parameterMap.get("novaSenha"));
        if (ns) {            
            novaSenha = ns;
            RequestContext.getCurrentInstance().update("container");
        }
    }

    public void solicitarSenha() {
        try {            
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
            ProfileDao dao = new ProfileDao(factory);
            Profile profile = dao.findByEmail(email);                                    
            if (profile!=null) {
                Random random = new Random();                                
                String senhaRandom = MD5.gerar(String.valueOf(random.nextDouble()));
                profile.setPassword(senhaRandom);
                dao.update(profile);
                MailClient.enviarNovaSenha(profile.getNome(), profile.getEmail(), Constants.IMG_TROCA_SENHA ,senhaRandom);
                FacesMessageUtil.info(Constants.SENHA_ENVIADA+email, Constants.SENHA_ENVIADA+email);            
            } else {
                FacesMessageUtil.erro(Constants.EMAIL_INVALIDO+email, Constants.EMAIL_INVALIDO+email);            
            }                        
            factory.close();                                    
        } catch (Exception e) {
            System.err.println("Erro ao solicitar nova senha "+e);
        }
        email = "";
    }   

    public boolean isNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(boolean novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
