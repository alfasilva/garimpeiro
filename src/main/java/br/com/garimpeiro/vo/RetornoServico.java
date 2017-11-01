/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.vo;

import br.com.garimpeiro.entities.ServicoPrestador;
import java.io.Serializable;

/**
 * VO
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class RetornoServico implements Serializable {

    private Integer bom;
    private Integer ruim;
    private boolean qualificar;
    private ServicoPrestador servicoPrestado;

    public RetornoServico() {}

    public RetornoServico(Integer bom, Integer ruim, ServicoPrestador servicoPrestado) {
        this.bom = bom;
        this.ruim = ruim;
        this.servicoPrestado = servicoPrestado;
    }
        
    public RetornoServico(ServicoPrestador servicoPrestado) {
        this.servicoPrestado = servicoPrestado;
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

    public ServicoPrestador getServicoPrestado() {
        return servicoPrestado;
    }

    public void setServicoPrestado(ServicoPrestador servicoPrestado) {
        this.servicoPrestado = servicoPrestado;
    }
}
