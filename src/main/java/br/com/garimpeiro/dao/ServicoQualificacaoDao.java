/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.dao;

import br.com.garimpeiro.entities.ServicoPrestador;
import br.com.garimpeiro.entities.ServicoQualificacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;

/**
 * Classe responsavel por manipular os dados referente a ServicoQualificacao no
 * banco.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class ServicoQualificacaoDao extends AbstractDao implements Serializable {

    public ServicoQualificacaoDao(EntityManagerFactory factory) {
        super(factory);
    }

    /**
     * Insert a new Service into the database.
     *
     * @param servicoQualificacao
     * @return
     */
    public ServicoQualificacao create(ServicoQualificacao servicoQualificacao) {
        return (ServicoQualificacao) super.create(servicoQualificacao, ServicoQualificacao.class);
    }

    /**
     * Delete a detached ServicoQualificacao from the database.
     *
     * @param servicoQualificacao
     */
    public void delete(ServicoQualificacao servicoQualificacao) {
        super.delete(servicoQualificacao);
    }

    /**
     * Find an Service by its primary key.
     *
     * @param id
     * @return
     */
    public ServicoQualificacao find(Long id) {
        return (ServicoQualificacao) super.find(ServicoQualificacao.class, id);
    }

    /**
     * Updates the state of a detached ServicoQualificacao.
     *
     * @param servicoQualificacao
     */
    public void update(ServicoQualificacao servicoQualificacao) {
        super.update(servicoQualificacao);
    }

    /**
     * Finds all ServicoQualificacao in the database.
     *
     * @return
     */
    public List findAll() {
        return super.findAll(ServicoQualificacao.class);
    }

    /**
     * Find all good qualification services.
     *
     * @return
     */
    public ServicoQualificacao findByBom() {
        return (ServicoQualificacao) super.findByParam(ServicoQualificacao.class, "Bom", true);
    }

    /**
     * Find all bad qualification services.
     *
     * @return
     */
    public ServicoQualificacao findRuim() {
        return (ServicoQualificacao) super.findByParam(ServicoQualificacao.class, "Bom", false);
    }

    public List<ServicoQualificacao> findAllByServicoPrestador(ServicoPrestador servicoPrestador) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("ServicoPrestador", servicoPrestador);
        List retorno = super.findByParamList(ServicoQualificacao.class, "ServicoPrestador", mapa);
        if(retorno!=null){
            return retorno;
        } 
        return new ArrayList<ServicoQualificacao>();
    }
}
