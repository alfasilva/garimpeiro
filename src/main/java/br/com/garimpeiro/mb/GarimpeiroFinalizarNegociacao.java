/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.mb;

import br.com.garimpeiro.dao.NegociacaoDao;
import br.com.garimpeiro.dao.NegociacaoFinalDao;
import br.com.garimpeiro.entities.Negociacao;
import br.com.garimpeiro.entities.NegociacaoFinal;
import br.com.garimpeiro.entities.Profile;
import br.com.garimpeiro.entities.ServicoPrestador;
import java.io.Serializable;
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
@ManagedBean(name = "ngFinMb")
@ViewScoped
public class GarimpeiroFinalizarNegociacao implements Serializable {

    private Profile usuarioA;
    private Profile usuarioB;
    private ServicoPrestador servicoPrestador;
    private Negociacao negociacao;
    private boolean exibir;
    private boolean finalizada;

    @ManagedProperty(value = "#{user}")
    private GarimpeiroUsuario user;

    @PostConstruct
    public void carregar() {
        try {
            usuarioA = user.getProfile();
            Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            if (usuarioA != null && StringUtils.isNotBlank(parameterMap.get("negotiate"))) {
                long negotiate = Long.parseLong(parameterMap.get("negotiate"));
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                NegociacaoDao negDao = new NegociacaoDao(factory);
                negociacao = negDao.find(negotiate);
                if (negociacao != null) {
                    servicoPrestador = negociacao.getServicoprestador();
                    exibir = true;
                    if (negociacao.getPrestador().equals(usuarioA)) {
                        usuarioB = negociacao.getCliente();
                    } else if (negociacao.getCliente().equals(usuarioA)) {
                        usuarioB = negociacao.getPrestador();
                    } else {
                        System.out.println("O Cara não deveria tentar fazer isso ...");
                        exibir = false;
                    }
                    if (exibir) {
                        NegociacaoFinalDao negFinDao = new NegociacaoFinalDao(factory);
                        List<NegociacaoFinal> finals = negFinDao.findNegociacao(negociacao);
                        for (NegociacaoFinal nf : finals) {
                            if (nf.getPessoa().equals(usuarioA)) {
                                finalizada = true;
                            }
                        }
                    }
                }
                factory.close();
            }
        } catch (Exception e) {
            RequestContext.getCurrentInstance().update("container");
            System.err.println("Erro ao carregar o profile: " + e);
        }
        RequestContext.getCurrentInstance().update("container");
    }

    public void finalizar() {
        try {
            usuarioA = user.getProfile();
            if (negociacao != null && usuarioA != null) {
                if (negociacao.getPrestador().equals(usuarioA)) {
                    usuarioB = negociacao.getCliente();
                } else if (negociacao.getCliente().equals(usuarioA)) {
                    usuarioB = negociacao.getPrestador();
                } else {
                    System.out.println("O Cara não deveria tentar fazer isso ...");
                    return;
                }
                if (!finalizada) {
                    EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                    NegociacaoFinalDao dao = new NegociacaoFinalDao(factory);
                    List<NegociacaoFinal> negociacoesEncerradas = dao.findNegociacao(negociacao);
                    if (negociacoesEncerradas != null && negociacoesEncerradas.size() > 0) {
                        dao.create(new NegociacaoFinal(new Date(), usuarioA, negociacao));
                        NegociacaoDao ngDao = new NegociacaoDao(factory);
                        negociacao.setDataFim(new Date());
                        ngDao.update(negociacao);
                    } else {
                        dao.create(new NegociacaoFinal(new Date(), usuarioA, negociacao));
                    }
                    factory.close();
                }
                FacesContext.getCurrentInstance().getExternalContext().redirect("mensagem.xhtml");
            }
        } catch (Exception e) {
            System.err.println("ERRO ao finalizar Negociação " + e);
        }
    }

    public Profile getUsuarioA() {
        return usuarioA;
    }

    public void setUsuarioA(Profile usuarioA) {
        this.usuarioA = usuarioA;
    }

    public Profile getUsuarioB() {
        return usuarioB;
    }

    public void setUsuarioB(Profile usuarioB) {
        this.usuarioB = usuarioB;
    }

    public ServicoPrestador getServicoPrestador() {
        return servicoPrestador;
    }

    public void setServicoPrestador(ServicoPrestador servicoPrestador) {
        this.servicoPrestador = servicoPrestador;
    }

    public Negociacao getNegociacao() {
        return negociacao;
    }

    public void setNegociacao(Negociacao negociacao) {
        this.negociacao = negociacao;
    }

    public boolean isExibir() {
        return exibir;
    }

    public void setExibir(boolean exibir) {
        this.exibir = exibir;
    }

    public GarimpeiroUsuario getUser() {
        return user;
    }

    public void setUser(GarimpeiroUsuario user) {
        this.user = user;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }
}
