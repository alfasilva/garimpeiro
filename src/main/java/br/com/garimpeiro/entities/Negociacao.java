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
 * Entidade que represendo o Objeto NEGOCIACAO na base de dados.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@Entity
@Table(name = "NEGOCIACAO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Negociacao.findAll", query = "SELECT n FROM Negociacao n"),
    @NamedQuery(name = "Negociacao.findById", query = "SELECT n FROM Negociacao n WHERE n.id = :id"),
    @NamedQuery(name = "Negociacao.findByDataIni", query = "SELECT n FROM Negociacao n WHERE n.dataIni = :dataIni"),
    @NamedQuery(name = "Negociacao.findByDataFim", query = "SELECT n FROM Negociacao n WHERE n.dataFim = :dataFim"),
    @NamedQuery(name = "Negociacao.findByClientePrestador", query = "SELECT n FROM Negociacao n WHERE n.cliente = :cliente and n.prestador = :prestador"),
    @NamedQuery(name = "Negociacao.findByClienteOrPrestador", query = "SELECT n FROM Negociacao n WHERE n.cliente = :cliente or n.prestador = :prestador"),
    @NamedQuery(name = "Negociacao.findByCliente", query = "SELECT n FROM Negociacao n WHERE n.cliente = :cliente"),
    @NamedQuery(name = "Negociacao.findByPrestador", query = "SELECT n FROM Negociacao n WHERE n.prestador = :prestador"),
    @NamedQuery(name = "Negociacao.findByServicoPrestador", query = "SELECT n FROM Negociacao n WHERE n.servicoprestador = :servicoprestador"),
    @NamedQuery(name = "Negociacao.findByOnly", query = "SELECT n FROM Negociacao n WHERE n.cliente = :cliente and n.prestador = :prestador and n.servicoprestador = :servicoprestador and dataFim = null")})
public class Negociacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "DATA_INI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataIni;
    @Column(name = "DATA_FIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    @ManyToOne(optional = true)
    private Profile cliente;
    @JoinColumn(name = "ID_PRESTADOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Profile prestador;
    @JoinColumn(name = "ID_SERVICO_PRESTADOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ServicoPrestador servicoprestador;

    public Negociacao() {
    }

    public Negociacao(Long id) {
        this.id = id;
    }

    public Negociacao(Long id, Date dataIni) {
        this.id = id;
        this.dataIni = dataIni;
    }

    public Negociacao(Date dataIni, Profile prestador, ServicoPrestador servicoprestador) {
        this.dataIni = dataIni;
        this.prestador = prestador;
        this.servicoprestador = servicoprestador;
    }

    public Negociacao(Date dataIni, Profile cliente, Profile prestador, ServicoPrestador servicoprestador) {
        this.dataIni = dataIni;
        this.cliente = cliente;
        this.prestador = prestador;
        this.servicoprestador = servicoprestador;
    }

    public Negociacao(Long id, Date dataIni, Date dataFim, Profile cliente, Profile prestador) {
        this.id = id;
        this.dataIni = dataIni;
        this.dataFim = dataFim;
        this.cliente = cliente;
        this.prestador = prestador;
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
        if (!(object instanceof Negociacao)) {
            return false;
        }
        Negociacao other = (Negociacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.garimpeiro.entities.Negociacao[ id=" + id + " ]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataIni() {
        return dataIni;
    }

    public void setDataIni(Date dataIni) {
        this.dataIni = dataIni;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
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

    public ServicoPrestador getServicoprestador() {
        return servicoprestador;
    }

    public void setServicoprestador(ServicoPrestador servicoprestador) {
        this.servicoprestador = servicoprestador;
    }  
}
