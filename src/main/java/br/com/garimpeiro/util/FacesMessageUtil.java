/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.util;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Classe utilitaria do sistema.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class FacesMessageUtil implements Serializable {

    public static void info(String a, String b) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, a, b);
        facesMessage.setSummary(a);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    /**
     *
     * @param a
     * @param b
     */
    public static void erro(String a, String b) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, a, b);
        facesMessage.setSummary(a);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
}
