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
 * Entidade que represendo o Objeto NEGOCIACAO_FINAL na base de dados.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@Entity
@Table(name = "NEGOCIACAO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NegociacaoFinal.findAll", query = "SELECT n FROM NegociacaoFinal n"),
    @NamedQuery(name = "NegociacaoFinal.findById", query = "SELECT n FROM NegociacaoFinal n WHERE n.id = :id"),
    @NamedQuery(name = "NegociacaoFinal.findByDescricao", query = "SELECT n FROM NegociacaoFinal n WHERE n.descricao = :descricao"),
    @NamedQuery(name = "NegociacaoFinal.findByData", query = "SELECT n FROM NegociacaoFinal n WHERE n.data = :data"),
    @NamedQuery(name = "NegociacaoFinal.findByNegociacao", query = "SELECT n FROM NegociacaoFinal n WHERE n.negociacao = :negociacao")})
public class NegociacaoFinal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;    
    @Column(name = "DESCRICAO")
    private String descricao;
    @Basic(optional = false)    
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "ID_PESSOA", referencedColumnName = "ID")
    @ManyToOne
    private Profile pessoa;
    @JoinColumn(name = "ID_NEGOCIACAO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Negociacao negociacao;

    public NegociacaoFinal() {
    }

    public NegociacaoFinal(Long id) {
        this.id = id;
    }

    public NegociacaoFinal(Long id, Date data) {
        this.id = id;
        this.data = data;
    }    

    public NegociacaoFinal(Date data, Profile pessoa, Negociacao negociacao) {
        this.data = data;
        this.pessoa = pessoa;
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
        if (!(object instanceof NegociacaoFinal)) {
            return false;
        }
        NegociacaoFinal other = (NegociacaoFinal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.garimpeiro.entities.NegociacaoFinal[ id=" + id + " ]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Profile getPessoa() {
        return pessoa;
    }

    public void setPessoa(Profile pessoa) {
        this.pessoa = pessoa;
    }

    public Negociacao getNegociacao() {
        return negociacao;
    }

    public void setNegociacao(Negociacao negociacao) {
        this.negociacao = negociacao;
    }
    
}
