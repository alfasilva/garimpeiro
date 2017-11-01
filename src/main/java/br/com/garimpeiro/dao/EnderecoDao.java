/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.dao;

import br.com.garimpeiro.entities.Endereco;
import br.com.garimpeiro.entities.Profile;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 * Classe responsavel por manipular os dados referente a Endereco no banco.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class EnderecoDao extends AbstractDao implements Serializable {

    public EnderecoDao(EntityManagerFactory factory) {
        super(factory);
    }

    /**
     * Insert a new Service into the database.
     *
     * @param endereco
     * @return
     */
    public Endereco create(Endereco endereco) {
        return (Endereco) super.create(endereco, Endereco.class);
    }

    /**
     * Delete a detached Endereco from the database.
     *
     * @param endereco
     */
    public void delete(Endereco endereco) {
        super.delete(endereco);
    }

    /**
     * Find an Service by its primary key.
     *
     * @param id
     * @return
     */
    public Endereco find(Long id) {
        return (Endereco) super.find(Endereco.class, id);
    }

    /**
     * Updates the state of a detached Endereco.
     *
     * @param endereco
     */
    public void update(Endereco endereco) {
        super.update(endereco);
    }

    /**
     * Finds all Endereco in the database.
     *
     * @return
     */
    public List findAll() {
        return super.findAll(Endereco.class);
    }
    /**
     * Find an Endereco by its profile.
     *
     * @param profile
     * @return 
     */
    public Endereco findByProfile(Profile profile) {
        return (Endereco) super.findByParam(Endereco.class, "Profile", profile);
    }
}
