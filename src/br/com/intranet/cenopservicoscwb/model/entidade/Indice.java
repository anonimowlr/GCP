/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.model.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author f5078775
 */
@Entity
@Table(name = "tb_indice")
public class Indice implements Serializable{
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "CD_INDICE", nullable = false, unique = true)
    private Integer codigoIndice;
    
    @Column(name = "NM_INDICE", nullable = false, unique = true)
    private String nomeIndice;

    @OneToMany(mappedBy = "indice", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ValorIndice> listaValorIndice = new ArrayList<>();
    
    
    public void adicionarValorIndice(ValorIndice valorIndice){
        valorIndice.setIndice(this);
        listaValorIndice.add(valorIndice);
    }    
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the codigoIndice
     */
    public Integer getCodigoIndice() {
        return codigoIndice;
    }

    /**
     * @param codigoIndice the codigoIndice to set
     */
    public void setCodigoIndice(Integer codigoIndice) {
        this.codigoIndice = codigoIndice;
    }

    /**
     * @return the nomeIndice
     */
    public String getNomeIndice() {
        return nomeIndice;
    }

    /**
     * @param nomeIndice the nomeIndice to set
     */
    public void setNomeIndice(String nomeIndice) {
        this.nomeIndice = nomeIndice;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.getId());
        hash = 67 * hash + Objects.hashCode(this.getCodigoIndice());
        hash = 67 * hash + Objects.hashCode(this.getNomeIndice());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Indice other = (Indice) obj;
        if (!Objects.equals(this.nomeIndice, other.nomeIndice)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.codigoIndice, other.codigoIndice)) {
            return false;
        }
        return true;
    }

    /**
     * @return the listaValorIndice
     */
    public List<ValorIndice> getListaValorIndice() {
        return listaValorIndice;
    }

    /**
     * @param listaValorIndice the listaValorIndice to set
     */
    public void setListaValorIndice(List<ValorIndice> listaValorIndice) {
        this.listaValorIndice = listaValorIndice;
    }
    
    
    
}
