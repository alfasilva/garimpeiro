/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.dao;

import br.com.garimpeiro.entities.Mensagem;
import br.com.garimpeiro.entities.Negociacao;
import br.com.garimpeiro.entities.Profile;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;

/**
 * Classe responsavel por manipular os dados referente a Mensagem no banco.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class MensagemDao extends AbstractDao implements Serializable {

    public MensagemDao(EntityManagerFactory factory) {
        super(factory);
    }

    /**
     * Insert a new Mensagem into the database.
     *
     * @param mensagem
     * @return
     */
    public Mensagem create(Mensagem mensagem) {
        return (Mensagem) super.create(mensagem, Mensagem.class);
    }

    /**
     * Delete a detached Mensagem from the database.
     *
     * @param mensagem
     */
    public void delete(Mensagem mensagem) {
        super.delete(mensagem);
    }

    /**
     * Find an Mensagem by its primary key.
     *
     * @param id
     * @return
     */
    public Mensagem find(Long id) {
        return (Mensagem) super.find(Mensagem.class, id);
    }

    /**
     * Updates the state of a detached Mensagem.
     *
     * @param mensagem
     */
    public void update(Mensagem mensagem) {
        super.update(mensagem);
    }

    /**
     * Finds all Mensagem in the database.
     *
     * @return
     */
    public List findAll() {
        return super.findAll(Mensagem.class);
    }

    /**
     * Localizar mensagens de uma negociacao.
     *
     * @param emissor
     * @param receptor
     * @param negociacao
     * @return
     */
    public List<Mensagem> localizarPorClientPrestNegociacao(Profile emissor, Profile receptor, Negociacao negociacao) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Emissor", emissor);
        mapa.put("Receptor", receptor);
        mapa.put("Negociacao", negociacao);
        return super.findByParamList(Mensagem.class, "EmissorReceptorNegociacao", mapa);
    }

    /**
     * Localizar mensagens
     *
     * @param receptor
     * @return
     */
    public List<Mensagem> findReceptorNaoLida(Profile receptor) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Receptor", receptor);
        return super.findByParamList(Mensagem.class, "ReceptorNaoLida", mapa);
    }

    public List<Mensagem> findReceptor(Profile receptor) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Receptor", receptor);
        return super.findByParamList(Mensagem.class, "Receptor", mapa);
    }
    
    public List<Mensagem> findAllByProfile(Profile pessoa) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Emissor", pessoa);
        mapa.put("Receptor", pessoa);
        return super.findByParamList(Mensagem.class, "DeOuPara", mapa);
    }    

    public List<Mensagem> localizarNegociacao(Negociacao negociacao) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Negociacao", negociacao);
        return super.findByParamList(Mensagem.class, "Negociacao", mapa);
    }

    public List<Mensagem> findEmissor(Profile emissor) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Emissor", emissor);
        return super.findByParamList(Mensagem.class, "EmissorNaoLida", mapa);
    }

}
