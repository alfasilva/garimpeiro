/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.vo;

import br.com.garimpeiro.entities.Endereco;
import br.com.garimpeiro.entities.Negociacao;
import br.com.garimpeiro.entities.ServicoPrestador;
import br.com.garimpeiro.entities.ServicoQualificacao;
import java.io.Serializable;

/**
 * VO
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class RetornoPesquisa implements Serializable {

    private Integer bom;
    private Integer ruim;
    private boolean qualificar;
    private Endereco endereco;
    private Negociacao negociacao;
    private ServicoPrestador servicoPrestado;
    private ServicoQualificacao servicoQualificacao;

    public RetornoPesquisa() {
    }

    public RetornoPesquisa(ServicoPrestador servicoPrestado) {
        this.servicoPrestado = servicoPrestado;
    }

    public RetornoPesquisa(Endereco endereco, ServicoPrestador servicoPrestado) {
        this.endereco = endereco;
        this.servicoPrestado = servicoPrestado;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public ServicoPrestador getServicoPrestado() {
        return servicoPrestado;
    }

    public void setServicoPrestado(ServicoPrestador servicoPrestado) {
        this.servicoPrestado = servicoPrestado;
    }

    public ServicoQualificacao getServicoQualificacao() {
        return servicoQualificacao;
    }

    public void setServicoQualificacao(ServicoQualificacao servicoQualificacao) {
        this.servicoQualificacao = servicoQualificacao;
    }

    public Integer getBom() {
        return bom;
    }

    public void setBom(Integer bom) {
        this.bom = bom;
    }

    public Integer getRuim() {
        return ruim;
    }

    public void setRuim(Integer ruim) {
        this.ruim = ruim;
    }

    public boolean isQualificar() {
        return qualificar;
    }

    public void setQualificar(boolean qualificar) {
        this.qualificar = qualificar;
    }

    public Negociacao getNegociacao() {
        return negociacao;
    }

    public void setNegociacao(Negociacao negociacao) {
        this.negociacao = negociacao;
    }
}
