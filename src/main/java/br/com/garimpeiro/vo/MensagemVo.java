/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.vo;

import java.io.Serializable;

/**
 * VO
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class MensagemVo implements Serializable {

    private String data;
    private String nomePessoa;
    private String mensagem;
    private boolean lida;

    public MensagemVo() {
    }

    public MensagemVo(String data, String nomePessoa, String mensagem, boolean lida) {
        this.data = data;
        this.nomePessoa = nomePessoa;
        this.mensagem = mensagem;
        this.lida = lida;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }
}
