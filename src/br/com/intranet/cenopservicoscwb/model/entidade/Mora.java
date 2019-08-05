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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author f5078775
 */
@Entity
@Table(name = "tb_mora")
public class Mora implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "DT_INICIO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    
    @Column(name = "VLR_MORA_PRE", nullable = false)
    private BigDecimal valorMoraPre;
    
    @Column(name = "VLR_MORA_POS", nullable = false)
    private BigDecimal valorMoraPos;

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
     * @return the dataInicio
     */
    public Date getDataInicio() {
        return dataInicio;
    }

    /**
     * @param dataInicio the dataInicio to set
     */
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * @return the valorMoraPre
     */
    public BigDecimal getValorMoraPre() {
        return valorMoraPre;
    }

    /**
     * @param valorMoraPre the valorMoraPre to set
     */
    public void setValorMoraPre(BigDecimal valorMoraPre) {
        this.valorMoraPre = valorMoraPre;
    }

    /**
     * @return the valorMoraPos
     */
    public BigDecimal getValorMoraPos() {
        return valorMoraPos;
    }

    /**
     * @param valorMoraPos the valorMoraPos to set
     */
    public void setValorMoraPos(BigDecimal valorMoraPos) {
        this.valorMoraPos = valorMoraPos;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.dataInicio);
        hash = 29 * hash + Objects.hashCode(this.valorMoraPre);
        hash = 29 * hash + Objects.hashCode(this.valorMoraPos);
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
        final Mora other = (Mora) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dataInicio, other.dataInicio)) {
            return false;
        }
        if (!Objects.equals(this.valorMoraPre, other.valorMoraPre)) {
            return false;
        }
        if (!Objects.equals(this.valorMoraPos, other.valorMoraPos)) {
            return false;
        }
        return true;
    }

    
}
