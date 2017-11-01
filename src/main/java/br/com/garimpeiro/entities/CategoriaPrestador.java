/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidade que represendo o Objeto CATEGORIA_PRESTADOR na base de dados.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@Entity
@Table(name = "CATEGORIA_PRESTADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaPrestador.findAll", query = "SELECT c FROM CategoriaPrestador c"),
    @NamedQuery(name = "CategoriaPrestador.findById", query = "SELECT c FROM CategoriaPrestador c WHERE c.id = :id"),
    @NamedQuery(name = "CategoriaPrestador.findByProfile", query = "SELECT c FROM CategoriaPrestador c WHERE c.profile = :profile")})
public class CategoriaPrestador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Categoria categoria;
    @JoinColumn(name = "ID_PROFILE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Profile profile;

    public CategoriaPrestador() {
    }

    public CategoriaPrestador(Long id) {
        this.id = id;
    }

    public CategoriaPrestador(Categoria categoria, Profile profile) {
        this.categoria = categoria;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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
        if (!(object instanceof CategoriaPrestador)) {
            return false;
        }
        CategoriaPrestador other = (CategoriaPrestador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.garimpeiro.entities.CategoriaPrestador[ id=" + id + " ]";
    }
}
