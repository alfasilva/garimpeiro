/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.dao;

import br.com.garimpeiro.entities.Categoria;
import br.com.garimpeiro.entities.CategoriaPrestador;
import br.com.garimpeiro.entities.Profile;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 * Classe responsavel por manipular os dados referente a Categoria do Prestador
 * no banco.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class CategoriaPrestadorDao extends AbstractDao implements Serializable {

    public CategoriaPrestadorDao(EntityManagerFactory factory) {
        super(factory);
    }

    /**
     * Insert a new Service into the database.
     *
     * @param categoriaPrestador
     * @return
     */
    public CategoriaPrestador create(CategoriaPrestador categoriaPrestador) {
        return (CategoriaPrestador) super.create(categoriaPrestador, CategoriaPrestador.class);
    }

    /**
     * Delete a detached CategoriaPrestador from the database.
     *
     * @param categoriaPrestador
     */
    public void delete(CategoriaPrestador categoriaPrestador) {
        super.delete(categoriaPrestador);
    }

    /**
     * Find an Service by its primary key.
     *
     * @param id
     * @return
     */
    public CategoriaPrestador find(Long id) {
        return (CategoriaPrestador) super.find(CategoriaPrestador.class, id);
    }

    /**
     * Updates the state of a detached CategoriaPrestador.
     *
     * @param categoriaPrestador
     */
    public void update(CategoriaPrestador categoriaPrestador) {
        super.update(categoriaPrestador);
    }

    /**
     * Finds all Services in the database.
     *
     * @return
     */
    public List findAll() {
        return super.findAll(CategoriaPrestador.class);
    }

    /**
     * Find an Categoria do Prestador by its profile.
     *
     * @param profile
     * @return
     */
    public CategoriaPrestador findByProfile(Profile profile) {
        return (CategoriaPrestador) super.findByParam(CategoriaPrestador.class, "Profile", profile);
    }

    /**
     * Find an Categoria do Prestador by its categoria.
     *
     * @param categoria
     * @return
     */
    public CategoriaPrestador findByCategoria(Categoria categoria) {
        return (CategoriaPrestador) super.findByParam(CategoriaPrestador.class, "Categoria", categoria);
    }
}
