/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.dao;

import br.com.garimpeiro.entities.Feedback;
import br.com.garimpeiro.entities.Profile;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 * Classe responsavel por manipular os dados referente a Feedback no banco.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class FeedBackDao extends AbstractDao implements Serializable {

    public FeedBackDao(EntityManagerFactory factory) {
        super(factory);
    }

    /**
     * Insert a new Service into the database.
     *
     * @param feedback
     * @return
     */
    public Feedback create(Feedback feedback) {
        return (Feedback) super.create(feedback, Feedback.class);
    }

    /**
     * Delete a detached Feedback from the database.
     *
     * @param feedback
     */
    public void delete(Feedback feedback) {
        super.delete(feedback);
    }

    /**
     * Find an Service by its primary key.
     *
     * @param id
     * @return
     */
    public Feedback find(Long id) {
        return (Feedback) super.find(Feedback.class, id);
    }

    /**
     * Updates the state of a detached Feedback.
     *
     * @param feedback
     */
    public void update(Feedback feedback) {
        super.update(feedback);
    }

    /**
     * Finds all Feedback in the database.
     *
     * @return
     */
    public List findAll() {
        return super.findAll(Feedback.class);
    }
    /**
     * Find an Feedback by its profile.
     *
     * @param profile
     * @return 
     */
    public Feedback findByProfile(Profile profile) {
        return (Feedback) super.findByParam(Feedback.class, "Profile", profile);
    }
}
