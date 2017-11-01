/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.mb;

import br.com.garimpeiro.dao.NegociacaoDao;
import br.com.garimpeiro.dao.ProfileDao;
import br.com.garimpeiro.dao.ServicoQualificacaoDao;
import br.com.garimpeiro.entities.Negociacao;
import br.com.garimpeiro.entities.Profile;
import br.com.garimpeiro.entities.ServicoPrestador;
import br.com.garimpeiro.entities.ServicoQualificacao;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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
@ManagedBean(name = "avaliacao")
@RequestScoped
public class GarimpeiroAvaliacao implements Serializable {

    private Integer bom;
    private Integer ruim;

    @ManagedProperty(value = "#{user}")
    private GarimpeiroUsuario user;

    @PostConstruct
    public void carregar() {

        try {
            Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            if (user.getProfile() != null && StringUtils.isNotBlank(parameterMap.get("uid")) && StringUtils.isNotBlank(parameterMap.get("business"))) {
                Long uid = Long.parseLong(parameterMap.get("uid"));
                Long bs = Long.parseLong(parameterMap.get("business"));
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                ProfileDao dao = new ProfileDao(factory);
                Profile prestador = dao.find(uid);
                NegociacaoDao ngDao = new NegociacaoDao(factory);
                Negociacao ng = ngDao.find(bs);
                String voto = parameterMap.get("vote");
                if (StringUtils.isNotEmpty(voto)) {
                    ServicoQualificacaoDao qualificacaoDao = new ServicoQualificacaoDao(factory);
                    qualificacaoDao.create(new ServicoQualificacao(Boolean.valueOf(voto), "-", new Date(), user.getProfile(), prestador, ng.getServicoprestador()));
                }
                factory.close();
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar servico " + e);
        }
        RequestContext.getCurrentInstance().update("container");
    }

    /**
     * Metodo utilizado para registrar a avalicao do servico.
     *
     * @param servicoPrestado
     * @param bom
     */
    public void votar(ServicoPrestador servicoPrestado, boolean bom) {        
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
            ServicoQualificacaoDao srvQualificacaoDao = new ServicoQualificacaoDao(factory);
            srvQualificacaoDao.create(new ServicoQualificacao(bom, "-", new Date(), user.getProfile(), servicoPrestado.getPrestador(), servicoPrestado));
            factory.close();
        } catch (Exception e) {
            System.err.println("Cadastro erro: " + e);
        }
    }

    public GarimpeiroUsuario getUser() {
        return user;
    }

    public void setUser(GarimpeiroUsuario user) {
        this.user = user;
    }

    public Integer getBom() {
        return bom;
    }

    public void setBom(Integer bom) {
        this.bom = bom;
    }

    public Integer getRuim() {
        return ruim;
    }

    public void setRuim(Integer ruim) {
        this.ruim = ruim;
    }
}
