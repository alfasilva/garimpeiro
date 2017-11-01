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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidade que represendo o Objeto SERVICO_QUALIFICACAO na base de dados.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@Entity
@Table(name = "SERVICO_QUALIFICACAO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServicoQualificacao.findAll", query = "SELECT s FROM ServicoQualificacao s"),
    @NamedQuery(name = "ServicoQualificacao.findById", query = "SELECT s FROM ServicoQualificacao s WHERE s.id = :id"),
    @NamedQuery(name = "ServicoQualificacao.findByBom", query = "SELECT s FROM ServicoQualificacao s WHERE s.bom = :bom"),
    @NamedQuery(name = "ServicoQualificacao.findByDescricao", query = "SELECT s FROM ServicoQualificacao s WHERE s.descricao = :descricao"),
    @NamedQuery(name = "ServicoQualificacao.findByData", query = "SELECT s FROM ServicoQualificacao s WHERE s.data = :data"),
    @NamedQuery(name = "ServicoQualificacao.findByServicoPrestador", query = "SELECT s FROM ServicoQualificacao s WHERE s.servicoPrestador = :servicoprestador")})
public class ServicoQualificacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "BOM")
    private Boolean bom;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Profile cliente;
    @JoinColumn(name = "ID_PRESTADOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Profile prestador;
    @JoinColumn(name = "ID_SERVICO_PRESTADOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ServicoPrestador servicoPrestador;

    public ServicoQualificacao() {}

    public ServicoQualificacao(Boolean bom, String descricao, Date data, Profile cliente, Profile prestador, ServicoPrestador servicoPrestador) {
        this.bom = bom;
        this.descricao = descricao;
        this.data = data;
        this.cliente = cliente;
        this.prestador = prestador;
        this.servicoPrestador = servicoPrestador;
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
        if (!(object instanceof ServicoQualificacao)) {
            return false;
        }
        ServicoQualificacao other = (ServicoQualificacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.garimpeiro.entities.ServicoQualificacao[ id=" + id + " ]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getBom() {
        return bom;
    }

    public void setBom(Boolean bom) {
        this.bom = bom;
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

    public Profile getCliente() {
        return cliente;
    }

    public void setCliente(Profile cliente) {
        this.cliente = cliente;
    }

    public Profile getPrestador() {
        return prestador;
    }

    public void setPrestador(Profile prestador) {
        this.prestador = prestador;
    }

    public ServicoPrestador getServicoPrestador() {
        return servicoPrestador;
    }

    public void setServicoPrestador(ServicoPrestador servicoPrestador) {
        this.servicoPrestador = servicoPrestador;
    }
}