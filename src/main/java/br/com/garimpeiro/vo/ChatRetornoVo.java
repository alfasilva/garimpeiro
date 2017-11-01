/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.vo;

import br.com.garimpeiro.entities.Mensagem;
import br.com.garimpeiro.entities.Negociacao;
import java.io.Serializable;
import java.util.List;

/**
 * VO
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class ChatRetornoVo implements Serializable {

    private Negociacao negociacao;
    private List<Mensagem> msg;

    public ChatRetornoVo() {}

    public ChatRetornoVo(Negociacao negociacao, List<Mensagem> msg) {
        this.negociacao = negociacao;
        this.msg = msg;
    }

    public Negociacao getNegociacao() {
        return negociacao;
    }

    public void setNegociacao(Negociacao negociacao) {
        this.negociacao = negociacao;
    }

    public List<Mensagem> getMsg() {
        return msg;
    }

    public void setMsg(List<Mensagem> msg) {
        this.msg = msg;
    }
}
