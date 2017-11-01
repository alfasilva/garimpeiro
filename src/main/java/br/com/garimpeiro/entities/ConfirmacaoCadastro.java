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
 * Entidade que represendo o Objeto CONFIRMACAO_CADASTRO na base de dados.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@Entity
@Table(name = "CONFIRMACAO_CADASTRO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfirmacaoCadastro.findAll", query = "SELECT c FROM ConfirmacaoCadastro c"),
    @NamedQuery(name = "ConfirmacaoCadastro.findById", query = "SELECT c FROM ConfirmacaoCadastro c WHERE c.id = :id"),
    @NamedQuery(name = "ConfirmacaoCadastro.findByToken", query = "SELECT c FROM ConfirmacaoCadastro c WHERE c.token = :token"),
    @NamedQuery(name = "ConfirmacaoCadastro.findByProfile", query = "SELECT c FROM ConfirmacaoCadastro c WHERE c.profile = :profile")})
public class ConfirmacaoCadastro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "TOKEN")
    private String token;
    @JoinColumn(name = "ID_PROFILE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Profile profile;

    public ConfirmacaoCadastro() {
    }

    public ConfirmacaoCadastro(Long id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param token
     */
    public ConfirmacaoCadastro(Long id, String token) {
        this.id = id;
        this.token = token;
    }

    /**
     * Construtor Principal
     *
     * @param token
     * @param profile
     */
    public ConfirmacaoCadastro(String token, Profile profile) {
        this.token = token;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
        if (!(object instanceof ConfirmacaoCadastro)) {
            return false;
        }
        ConfirmacaoCadastro other = (ConfirmacaoCadastro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.garimpeiro.entities.ConfirmacaoCadastro[ id=" + id + " ]";
    }
}
