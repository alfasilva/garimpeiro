/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidade que represendo o Objeto MENSAGEM na base de dados.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@Entity
@Table(name = "MENSAGEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensagem.findAll", query = "SELECT m FROM Mensagem m"),
    @NamedQuery(name = "Mensagem.findById", query = "SELECT m FROM Mensagem m WHERE m.id = :id"),
    @NamedQuery(name = "Mensagem.findByTexto", query = "SELECT m FROM Mensagem m WHERE m.texto = :texto"),
    @NamedQuery(name = "Mensagem.findByData", query = "SELECT m FROM Mensagem m WHERE m.data = :data"),
    @NamedQuery(name = "Mensagem.findByLida", query = "SELECT m FROM Mensagem m WHERE m.lida = :lida"),
    @NamedQuery(name = "Mensagem.findByReceptor", query = "SELECT m FROM Mensagem m WHERE m.receptor = :receptor"),
    @NamedQuery(name = "Mensagem.findByNegociacao", query = "SELECT m FROM Mensagem m WHERE m.negociacao = :negociacao ORDER BY m.id DESC"),
    @NamedQuery(name = "Mensagem.findByReceptorNaoLida", query = "SELECT m FROM Mensagem m WHERE m.receptor = :receptor and m.lida = false"),
    @NamedQuery(name = "Mensagem.findByEmissorNaoLida", query = "SELECT m FROM Mensagem m WHERE m.emissor = :emissor and m.lida = false"),
    @NamedQuery(name = "Mensagem.findByEmissorReceptorNegociacao", query = "SELECT m FROM Mensagem m WHERE m.emissor = :emissor and m.receptor = :receptor and m.negociacao = :negociacao"),
    @NamedQuery(name = "Mensagem.findByDeOuPara", query = "SELECT m FROM Mensagem m WHERE (m.negociacao.dataFim = null) and (m.emissor = :emissor or m.receptor = :receptor) ORDER BY m.id DESC")})
public class Mensagem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "TEXTO")
    private String texto;
    @Basic(optional = false)
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "LIDA")
    private Boolean lida;
    @JoinColumn(name = "ID_EMISSOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Profile emissor;
    @JoinColumn(name = "ID_RECEPTOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Profile receptor;
    @JoinColumn(name = "ID_NEGOCIACAO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Negociacao negociacao;

    public Mensagem() {
    }

    public Mensagem(Long id) {
        this.id = id;
    }

    public Mensagem(String texto, Date data, Profile emissor, Profile receptor, Negociacao negociacao) {
        this.texto = texto;
        this.data = data;
        this.emissor = emissor;
        this.receptor = receptor;
        this.negociacao = negociacao;
    }
   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensagem)) {
            return false;
        }
        Mensagem other = (Mensagem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.garimpeiro.entities.Mensagem[ id=" + id + " ]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Boolean getLida() {
        return lida;
    }

    public void setLida(Boolean lida) {
        this.lida = lida;
    }

    public Profile getEmissor() {
        return emissor;
    }

    public void setEmissor(Profile emissor) {
        this.emissor = emissor;
    }

    public Profile getReceptor() {
        return receptor;
    }

    public void setReceptor(Profile receptor) {
        this.receptor = receptor;
    }

    public Negociacao getNegociacao() {
        return negociacao;
    }

    public void setNegociacao(Negociacao negociacao) {
        this.negociacao = negociacao;
    }

}
