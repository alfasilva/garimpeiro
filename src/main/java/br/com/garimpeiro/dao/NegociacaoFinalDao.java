/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.dao;

import br.com.garimpeiro.entities.Negociacao;
import br.com.garimpeiro.entities.NegociacaoFinal;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;

/**
 * Classe responsavel por manipular os dados referente a Negociacoes encerradas no banco.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class NegociacaoFinalDao extends AbstractDao implements Serializable {

    public NegociacaoFinalDao(EntityManagerFactory factory) {
        super(factory);
    }

    /**
     * Insert a new Negociacao into the database.
     *
     * @param negociacaoFinal
     * @return
     */
    public NegociacaoFinal create(NegociacaoFinal negociacaoFinal) {
        return (NegociacaoFinal) super.create(negociacaoFinal, NegociacaoFinal.class);
    }

    /**
     * Delete a detached Negociacao from the database.
     *
     * @param negociacaoFinal
     */
    public void delete(NegociacaoFinal negociacaoFinal) {
        super.delete(negociacaoFinal);
    }

    /**
     * Find an Negociacao by its primary key.
     *
     * @param id
     * @return
     */
    public NegociacaoFinal find(Long id) {
        return (NegociacaoFinal) super.find(NegociacaoFinal.class, id);
    }

    /**
     * Updates the state of a detached Negociacao.
     *
     * @param negociacaoFinal
     */
    public void update(NegociacaoFinal negociacaoFinal) {
        super.update(negociacaoFinal);
    }

    /**
     * Finds all Negociacao in the database.
     *
     * @return
     */
    public List<NegociacaoFinal> findAll() {
        return super.findAll(NegociacaoFinal.class);
    }

    /**
     * Retona Lista de negociacoes encerradas, por uma determinada negociação.
     *
     * @param negociacao
     * @return
     */
    public List<NegociacaoFinal> findNegociacao(Negociacao negociacao) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Negociacao", negociacao);
        return super.findByParamList(NegociacaoFinal.class, "Negociacao", mapa);
    }
}
