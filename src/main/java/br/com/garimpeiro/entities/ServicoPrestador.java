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
 * Entidade que represendo o Objeto SERVICO_PRESTADOR na base de dados.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@Entity
@Table(name = "SERVICO_PRESTADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServicoPrestador.findAll", query = "SELECT s FROM ServicoPrestador s"),
    @NamedQuery(name = "ServicoPrestador.findById", query = "SELECT s FROM ServicoPrestador s WHERE s.id = :id"),
    @NamedQuery(name = "ServicoPrestador.findByValor", query = "SELECT s FROM ServicoPrestador s WHERE s.valor = :valor"),
    @NamedQuery(name = "ServicoPrestador.findByDataIni", query = "SELECT s FROM ServicoPrestador s WHERE s.dataIni = :dataIni"),
    @NamedQuery(name = "ServicoPrestador.findByDataFim", query = "SELECT s FROM ServicoPrestador s WHERE s.dataFim = :dataFim"),
    @NamedQuery(name = "ServicoPrestador.findByDescricao", query = "SELECT s FROM ServicoPrestador s WHERE s.descricao = :descricao"),
    @NamedQuery(name = "ServicoPrestador.findByServico", query = "SELECT s FROM ServicoPrestador s WHERE (s.servico = :servico) and (s.dataFim >= sysdate() or s.dataFim = null) ORDER BY s.id DESC"),
    @NamedQuery(name = "ServicoPrestador.findByPrestador", query = "SELECT s FROM ServicoPrestador s WHERE (s.prestador = :prestador) and (s.dataFim >= sysdate() or s.dataFim = null) ORDER BY s.id DESC")})
public class ServicoPrestador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private long valor;
    @Basic(optional = false)
    @Column(name = "DATA_INI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataIni;
    @Column(name = "DATA_FIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "URL")
    private String url;
    @JoinColumn(name = "ID_PRESTADOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Profile prestador;
    @JoinColumn(name = "ID_SERVICO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Servico servico;

    public ServicoPrestador() {
    }

    public ServicoPrestador(Long id) {
        this.id = id;
    }

    public ServicoPrestador(Long id, long valor, Date dataIni) {
        this.id = id;
        this.valor = valor;
        this.dataIni = dataIni;
    }

    public ServicoPrestador(long valor, Date dataIni, String descricao, String url, Profile prestador, Servico servico) {
        this.valor = valor;
        this.dataIni = dataIni;
        this.descricao = descricao;
        this.url = url;
        this.prestador = prestador;
        this.servico = servico;
    }

    public ServicoPrestador(long valor, Date dataIni, Date dataFim, String descricao, String url, Profile prestador, Servico servico) {
        this.valor = valor;
        this.dataIni = dataIni;
        this.dataFim = dataFim;
        this.descricao = descricao;
        this.url = url;
        this.prestador = prestador;
        this.servico = servico;
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
        if (!(object instanceof ServicoPrestador)) {
            return false;
        }
        ServicoPrestador other = (ServicoPrestador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.garimpeiro.entities.ServicoPrestador[ id=" + id + " ]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Profile getPrestador() {
        return prestador;
    }

    public void setPrestador(Profile prestador) {
        this.prestador = prestador;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
