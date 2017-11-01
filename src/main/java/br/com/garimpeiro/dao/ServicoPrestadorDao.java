/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.dao;

import br.com.garimpeiro.entities.Profile;
import br.com.garimpeiro.entities.Servico;
import br.com.garimpeiro.entities.ServicoPrestador;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;

/**
 * Classe responsavel por manipular os dados referente a Categoria do Prestador
 * no banco.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class ServicoPrestadorDao extends AbstractDao implements Serializable {

    public ServicoPrestadorDao(EntityManagerFactory factory) {
        super(factory);
    }

    /**
     * Insert a new Service into the database.
     *
     * @param servicoPrestador
     * @return
     */
    public ServicoPrestador create(ServicoPrestador servicoPrestador) {
        return (ServicoPrestador) super.create(servicoPrestador, ServicoPrestador.class);
    }

    /**
     * Delete a detached ServicoPrestador from the database.
     *
     * @param servicoPrestador
     */
    public void delete(ServicoPrestador servicoPrestador) {
        super.delete(servicoPrestador);
    }

    /**
     * Find an Service by its primary key.
     *
     * @param id
     * @return
     */
    public ServicoPrestador find(Long id) {
        return (ServicoPrestador) super.find(ServicoPrestador.class, id);
    }

    /**
     * Updates the state of a detached ServicoPrestador.
     *
     * @param servicoPrestador
     */
    public void update(ServicoPrestador servicoPrestador) {
        super.update(servicoPrestador);
    }

    /**
     * Finds all Services in the database.
     *
     * @return
     */
    public List findAll() {
        return super.findAll(ServicoPrestador.class);
    }

    public ServicoPrestador findById(Long id) {
        return (ServicoPrestador) super.findById(ServicoPrestador.class, id);
    }

    public List<ServicoPrestador> findByProfile(Profile prestador) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Prestador", prestador);
        return super.findByParamList(ServicoPrestador.class, "Prestador", mapa);
    }

    public List<ServicoPrestador> findByServico(Servico servico) {
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("Servico", servico);
        return super.findByParamList(ServicoPrestador.class, "Servico", mapa);
    }
}
