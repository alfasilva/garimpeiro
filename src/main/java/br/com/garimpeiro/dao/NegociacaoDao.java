/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.dao;

import br.com.garimpeiro.entities.Negociacao;
import br.com.garimpeiro.entities.Profile;
import br.com.garimpeiro.entities.ServicoPrestador;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;

/**
 * Classe responsavel por manipular os dados referente a Negociacao no banco.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class NegociacaoDao extends AbstractDao implements Serializable {

    public NegociacaoDao(EntityManagerFactory factory) {
        super(factory);
    }

    /**
     * Insert a new Negociacao into the database.
     *
     * @param negociacao
     * @return
     */
    public Negociacao create(Negociacao negociacao) {
        return (Negociacao) super.create(negociacao, Negociacao.class);
    }

    /**
     * Delete a detached Negociacao from the database.
     *
     * @param negociacao
     */
    public void delete(Negociacao negociacao) {
        super.delete(negociacao);
    }

    /**
     * Find an Negociacao by its primary key.
     *
     * @param id
     * @return
     */
    public Negociacao find(Long id) {
        return (Negociacao) super.find(Negociacao.class, id);
    }

    /**
     * Updates the state of a detached Negociacao.
     *
     * @param negociacao
     */
    public void update(Negociacao negociacao) {
        super.update(negociacao);
    }

    /**
     * Finds all Negociacao in the database.
     *
     * @return
     */
    public List<Negociacao> findAll() {
        return super.findAll(Negociacao.class);
    }

    /**
     * Retona Lista de negociacoes feitas entre o Ciente e Prestador de Servico.
     *
     * @param cliente
     * @param prestador
     * @return
     */
    public List<Negociacao> findCientePrestador(Profile cliente, Profile prestador) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Cliente", cliente);
        mapa.put("Prestador", prestador);
        return super.findByParamList(Negociacao.class, "ClientePrestador", mapa);
    }

    /**
     * Retona Lista de negociacoes feitas per um determinado Ciente.
     *
     * @param cliente
     * @return
     */
    public List<Negociacao> findCliente(Profile cliente) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Cliente", cliente);
        return super.findByParamList(Negociacao.class, "Cliente", mapa);
    }

    /**
     * Retona Lista de negociacoes feitas per um determinado Prestador.
     *
     * @param prestador
     * @return
     */
    public List<Negociacao> findPrestador(Profile prestador) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Prestador", prestador);
        return super.findByParamList(Negociacao.class, "Prestador", mapa);
    }

    public List<Negociacao> findCienteOrPrestador(Profile profile) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Cliente", profile);
        mapa.put("Prestador", profile);
        return super.findByParamList(Negociacao.class, "ClienteOrPrestador", mapa);
    }

    /**
     * Localiza a negociacao entre o Cliente, Prestador e serviço Prestado.
     *
     * @param cliente
     * @param prestador
     * @param servicoprestador
     * @return
     */
    public Negociacao findOnly(Profile cliente, Profile prestador, ServicoPrestador servicoprestador) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Cliente", cliente);
        mapa.put("Prestador", prestador);
        mapa.put("ServicoPrestador", servicoprestador);
        List<Negociacao> retorno = super.findByParamList(Negociacao.class, "Only", mapa);
        return retorno.isEmpty() ? null : retorno.get(0);
    }

    public Negociacao findByServicoPrestador(ServicoPrestador servicoPrestador) {
        List<Negociacao> r = findAllByServicoPrestador(servicoPrestador);
        if (r != null && r.size() > 0) {
            return r.get(0);
        }
        return null;
    }

    public List<Negociacao> findAllByServicoPrestador(ServicoPrestador servicoPrestador) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("ServicoPrestador", servicoPrestador);
        return super.findByParamList(Negociacao.class, "ServicoPrestador", mapa);
    }

}
