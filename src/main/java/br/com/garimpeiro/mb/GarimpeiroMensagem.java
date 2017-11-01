/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.mb;

import br.com.garimpeiro.dao.MensagemDao;
import br.com.garimpeiro.dao.NegociacaoDao;
import br.com.garimpeiro.dao.ProfileDao;
import br.com.garimpeiro.dao.ServicoPrestadorDao;
import br.com.garimpeiro.entities.Mensagem;
import br.com.garimpeiro.entities.Negociacao;
import br.com.garimpeiro.entities.Profile;
import br.com.garimpeiro.entities.ServicoPrestador;
import br.com.garimpeiro.vo.ChatRetornoVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
@ManagedBean(name = "msgMb")
@ViewScoped
public class GarimpeiroMensagem implements Serializable {

    private Long uid;
    private Long bs;
    private String texto;
    private Profile de;
    private boolean exibirLink;
    private Profile para;
    private Negociacao negociacao;
    private ServicoPrestador servicoPrestador;
    private List<Mensagem> mensagens;
    private List<ChatRetornoVo> retorno;

    @ManagedProperty(value = "#{user}")
    private GarimpeiroUsuario user;

    @PostConstruct
    public void carregar() {
        try {
            retorno = new ArrayList<ChatRetornoVo>();
            de = user.getProfile();
            Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            if (de != null && StringUtils.isNotBlank(parameterMap.get("uid")) && StringUtils.isNotBlank(parameterMap.get("business"))) {
                uid = Long.parseLong(parameterMap.get("uid"));
                bs = Long.parseLong(parameterMap.get("business"));
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                ProfileDao proDao = new ProfileDao(factory);
                MensagemDao msgDao = new MensagemDao(factory);
                NegociacaoDao negDao = new NegociacaoDao(factory);
                ServicoPrestadorDao srvPresDao = new ServicoPrestadorDao(factory);
                para = proDao.find(uid);
                mensagens = new ArrayList<Mensagem>();
                servicoPrestador = srvPresDao.find(bs);
                negociacao = negDao.findOnly(de, para, servicoPrestador);
                if (negociacao == null) {
                    negociacao = negDao.findOnly(para, de, servicoPrestador);
                }

                if (negociacao != null) {
                    mensagens = msgDao.localizarNegociacao(negociacao);
                    if (mensagens != null && mensagens.size() > 0) {
                        retorno.add(new ChatRetornoVo(negociacao, mensagens));
                    }
                }
                exibirLink = false;
                factory.close();
            } else {
                para = null;
                negociacao = null;
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                MensagemDao msgDao = new MensagemDao(factory);
                mensagens = msgDao.findAllByProfile(de);
                Map<Long, Mensagem> map = new HashMap<Long, Mensagem>();
                for (Mensagem m : mensagens) {
                    map.put(m.getNegociacao().getId(), m);
                }
                for (Map.Entry<Long, Mensagem> e : map.entrySet()) {
                    List<Mensagem> lista = new ArrayList<Mensagem>();
                    for (Mensagem m : mensagens) {
                        if (m.getNegociacao().getId().compareTo(e.getKey()) == 0) {
                            lista.add(m);
                        }
                    }
                    retorno.add(new ChatRetornoVo(lista.get(0).getNegociacao(), lista));
                }
                exibirLink = true;
                factory.close();
            }
        } catch (Exception e) {
            RequestContext.getCurrentInstance().update("container");
            System.err.println("Erro ao carregar o profile: " + e);
        }
        RequestContext.getCurrentInstance().update("container");
    }

    public void enviar() {
        try {
            if (StringUtils.isNotEmpty(texto) && de != null && para != null && servicoPrestador != null) {
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                retorno = new ArrayList<ChatRetornoVo>();
                if (negociacao == null) {
                    NegociacaoDao negDao = new NegociacaoDao(factory);
                    negociacao = negDao.create(new Negociacao(new Date(), de, para, servicoPrestador));
                }
                MensagemDao msgDao = new MensagemDao(factory);
                Mensagem msg = msgDao.create(new Mensagem(texto, new Date(), de, para, negociacao));
                mensagens.add(msg);
                retorno.add(new ChatRetornoVo(negociacao, mensagens));
                texto = "";
                factory.close();
            }
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem: " + e);
        }
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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Profile getDe() {
        return de;
    }

    public void setDe(Profile de) {
        this.de = de;
    }

    public boolean isExibirLink() {
        return exibirLink;
    }

    public void setExibirLink(boolean exibirLink) {
        this.exibirLink = exibirLink;
    }

    public Profile getPara() {
        return para;
    }

    public void setPara(Profile para) {
        this.para = para;
    }

    public Negociacao getNegociacao() {
        return negociacao;
    }

    public void setNegociacao(Negociacao negociacao) {
        this.negociacao = negociacao;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public List<ChatRetornoVo> getRetorno() {
        return retorno;
    }

    public void setRetorno(List<ChatRetornoVo> retorno) {
        this.retorno = retorno;
    }

    public GarimpeiroUsuario getUser() {
        return user;
    }

    public void setUser(GarimpeiroUsuario user) {
        this.user = user;
    }

    public ServicoPrestador getServicoPrestador() {
        return servicoPrestador;
    }

    public void setServicoPrestador(ServicoPrestador servicoPrestador) {
        this.servicoPrestador = servicoPrestador;
    }
}
