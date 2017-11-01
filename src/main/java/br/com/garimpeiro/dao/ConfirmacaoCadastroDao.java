/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.dao;

import br.com.garimpeiro.entities.ConfirmacaoCadastro;
import br.com.garimpeiro.entities.Profile;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 * Classe responsavel por manipular os dados referente a Confirmacao do Cadastro
 * no banco.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class ConfirmacaoCadastroDao extends AbstractDao implements Serializable {

    public ConfirmacaoCadastroDao(EntityManagerFactory factory) {
        super(factory);
    }

    /**
     * Insert a new Service into the database.
     *
     * @param confirmacaoCadastro
     * @return
     */
    public ConfirmacaoCadastro create(ConfirmacaoCadastro confirmacaoCadastro) {
        return (ConfirmacaoCadastro) super.create(confirmacaoCadastro, ConfirmacaoCadastro.class);
    }

    /**
     * Delete a detached ConfirmacaoCadastro from the database.
     *
     * @param confirmacaoCadastro
     */
    public void delete(ConfirmacaoCadastro confirmacaoCadastro) {
        super.delete(confirmacaoCadastro);
    }

    /**
     * Find an Service by its primary key.
     *
     * @param id
     * @return
     */
    public ConfirmacaoCadastro find(Long id) {
        return (ConfirmacaoCadastro) super.find(ConfirmacaoCadastro.class, id);
    }

    /**
     * Updates the state of a detached ConfirmacaoCadastro.
     *
     * @param confirmacaoCadastro
     */
    public void update(ConfirmacaoCadastro confirmacaoCadastro) {
        super.update(confirmacaoCadastro);
    }

    /**
     * Finds all Services in the database.
     *
     * @return
     */
    public List findAll() {
        return super.findAll(ConfirmacaoCadastro.class);
    }

    /**
     * Find an ConfirmacaoCadastro by its primary key.
     *
     * @param token
     * @return
     */
    public ConfirmacaoCadastro findByToken(String token) {
        return (ConfirmacaoCadastro) super.findByParam(ConfirmacaoCadastro.class, "Token", token);
    }

    /**
     * Find an ConfirmacaoCadastro by its profile key.
     * 
     * @param profile
     * @return
     */
    public ConfirmacaoCadastro findByProfile(Profile profile) {
        return (ConfirmacaoCadastro) super.findByParam(ConfirmacaoCadastro.class, "Profile", profile);
    }
}
