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
@Table(name = "tb_expurgo")
public class Expurgo implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "MARCADOR")    
    private String marcador;

    @OneToMany(mappedBy = "expurgo", cascade = CascadeType.ALL)
    private List<ValorExpurgo> listaValorExpurgo = new ArrayList<>();
    
    
    
    /**
     * @return the marcador
     */
    public String getMarcador() {
        return marcador;
    }

    /**
     * @param marcador the marcador to set
     */
    public void setMarcador(String marcador) {
        this.marcador = marcador;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.getMarcador());
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
        final Expurgo other = (Expurgo) obj;
        if (!Objects.equals(this.marcador, other.marcador)) {
            return false;
        }
        return true;
    }

    /**
     * @return the listaValorExpurgo
     */
    public List<ValorExpurgo> getListaValorExpurgo() {
        return listaValorExpurgo;
    }

    /**
     * @param listaValorExpurgo the listaValorExpurgo to set
     */
    public void setListaValorExpurgo(List<ValorExpurgo> listaValorExpurgo) {
        this.listaValorExpurgo = listaValorExpurgo;
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
    
    
}
