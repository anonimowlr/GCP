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
@Table(name = "tb_multa")
public class Multa implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "TX_MULTA")
    private BigDecimal taxaMulta;
    
    @Column(name = "VLR_MULTA")
    private BigDecimal valorMulta;

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
     * @return the taxaMulta
     */
    public BigDecimal getTaxaMulta() {
        return taxaMulta;
    }

    /**
     * @param taxaMulta the taxaMulta to set
     */
    public void setTaxaMulta(BigDecimal taxaMulta) {
        this.taxaMulta = taxaMulta;
    }

    /**
     * @return the valorMulta
     */
    public BigDecimal getValorMulta() {
        return valorMulta;
    }

    /**
     * @param valorMulta the valorMulta to set
     */
    public void setValorMulta(BigDecimal valorMulta) {
        this.valorMulta = valorMulta;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.taxaMulta);
        hash = 97 * hash + Objects.hashCode(this.valorMulta);
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
        final Multa other = (Multa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.taxaMulta, other.taxaMulta)) {
            return false;
        }
        if (!Objects.equals(this.valorMulta, other.valorMulta)) {
            return false;
        }
        return true;
    }
    
    
}
