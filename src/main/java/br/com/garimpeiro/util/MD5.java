/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.util;

import java.io.Serializable;
import java.security.MessageDigest;

/**
 * Classe Utilitaria responsavel por converter a senha do usuario em um hash.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class MD5 implements Serializable {

    /**
     * Gerar MD5 apartir de uma String qualquer
     *
     * @param input
     * @return
     */
    public static String gerar(String input) {
        try {
            MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; i++) {
                sb.append(String.format("%02x", array[i]));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
