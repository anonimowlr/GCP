/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author f5078775
 */
@Entity
@Table(name = "tb_honorario")
public class Honorario implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "TX_HONORARIO", nullable = false)
    private BigDecimal taxaHonorario;
    
    @Column(name = "VLR_HONORARIO", nullable = false)
    private BigDecimal valorHonorario;

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
     * @return the taxaHonorario
     */
    public BigDecimal getTaxaHonorario() {
        return taxaHonorario;
    }

    /**
     * @param taxaHonorario the taxaHonorario to set
     */
    public void setTaxaHonorario(BigDecimal taxaHonorario) {
        this.taxaHonorario = taxaHonorario;
    }

    /**
     * @return the valorHonorario
     */
    public BigDecimal getValorHonorario() {
        return valorHonorario;
    }

    /**
     * @param valorHonorario the valorHonorario to set
     */
    public void setValorHonorario(BigDecimal valorHonorario) {
        this.valorHonorario = valorHonorario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.taxaHonorario);
        hash = 53 * hash + Objects.hashCode(this.valorHonorario);
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
        final Honorario other = (Honorario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.taxaHonorario, other.taxaHonorario)) {
            return false;
        }
        if (!Objects.equals(this.valorHonorario, other.valorHonorario)) {
            return false;
        }
        return true;
    }
    
    
    
}
