/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author f5078775
 */
@Entity
@Table(name = "tb_valor_expurgo")
public class ValorExpurgo implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "DT_EXPURGO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataExpurgo;
    
    @Column(name = "INDICE_EXPURGO", nullable = false, scale = 6, precision = 6)
    private BigDecimal indiceExpurgo;
    
    @ManyToOne
    @JoinColumn(name = "CD_EXPURGO", referencedColumnName = "id")
    private Expurgo expurgo;
    
    
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
     * @return the dataExpurgo
     */
    public Date getDataExpurgo() {
        return dataExpurgo;
    }

    /**
     * @param dataExpurgo the dataExpurgo to set
     */
    public void setDataExpurgo(Date dataExpurgo) {
        this.dataExpurgo = dataExpurgo;
    }

    /**
     * @return the indiceExpurgo
     */
    public BigDecimal getIndiceExpurgo() {
        return indiceExpurgo;
    }

    /**
     * @param indiceExpurgo the indiceExpurgo to set
     */
    public void setIndiceExpurgo(BigDecimal indiceExpurgo) {
        this.indiceExpurgo = indiceExpurgo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.getId());
        hash = 37 * hash + Objects.hashCode(this.getDataExpurgo());
        hash = 37 * hash + Objects.hashCode(this.getIndiceExpurgo());
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
        final ValorExpurgo other = (ValorExpurgo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dataExpurgo, other.dataExpurgo)) {
            return false;
        }
        if (!Objects.equals(this.indiceExpurgo, other.indiceExpurgo)) {
            return false;
        }
        return true;
    }

    /**
     * @return the expurgo
     */
    public Expurgo getExpurgo() {
        return expurgo;
    }

    /**
     * @param expurgo the expurgo to set
     */
    public void setExpurgo(Expurgo expurgo) {
        this.expurgo = expurgo;
    }
    
    
}
