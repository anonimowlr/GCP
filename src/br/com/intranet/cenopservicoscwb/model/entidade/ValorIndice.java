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
@Table(name = "tb_valor_indice")
public class ValorIndice implements Serializable{
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "VLR_INDICE", nullable = false, scale = 6, precision = 6)
    private BigDecimal valorIndice;
    
    @Column(name = "DT_VLR_INDICE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataValorIndice;
    
    
    @ManyToOne
    @JoinColumn(name = "ID_INDICE", referencedColumnName = "id")
    private Indice indice;
    
    

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
     * @return the valorIndice
     */
    public BigDecimal getValorIndice() {
        return valorIndice;
    }

    /**
     * @param valorIndice the valorIndice to set
     */
    public void setValorIndice(BigDecimal valorIndice) {
        this.valorIndice = valorIndice;
    }

    /**
     * @return the dataValorIndice
     */
    public Date getDataValorIndice() {
        return dataValorIndice;
    }

    /**
     * @param dataValorIndice the dataValorIndice to set
     */
    public void setDataValorIndice(Date dataValorIndice) {
        this.dataValorIndice = dataValorIndice;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.getId());
        hash = 59 * hash + Objects.hashCode(this.getValorIndice());
        hash = 59 * hash + Objects.hashCode(this.getDataValorIndice());
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
        final ValorIndice other = (ValorIndice) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.valorIndice, other.valorIndice)) {
            return false;
        }
        if (!Objects.equals(this.dataValorIndice, other.dataValorIndice)) {
            return false;
        }
        return true;
    }

    /**
     * @return the indice
     */
    public Indice getIndice() {
        return indice;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(Indice indice) {
        this.indice = indice;
    }
    
    
}
