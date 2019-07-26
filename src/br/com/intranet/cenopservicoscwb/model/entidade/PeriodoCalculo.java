/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.model.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
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
@Table(name = "tb_periodo_calculo")
public class PeriodoCalculo implements Serializable{
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "DT_INICIO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicioCalculo;
    
    @Column(name = "DT_FINAL", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataFinalCalculo;

    
    @OneToOne(cascade = CascadeType.ALL)
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
     * @return the dataInicioCalculo
     */
    public Date getDataInicioCalculo() {
        return dataInicioCalculo;
    }

    /**
     * @param dataInicioCalculo the dataInicioCalculo to set
     */
    public void setDataInicioCalculo(Date dataInicioCalculo) {
        this.dataInicioCalculo = dataInicioCalculo;
    }

    /**
     * @return the dataFinalCalculo
     */
    public Date getDataFinalCalculo() {
        return dataFinalCalculo;
    }

    /**
     * @param dataFinalCalculo the dataFinalCalculo to set
     */
    public void setDataFinalCalculo(Date dataFinalCalculo) {
        this.dataFinalCalculo = dataFinalCalculo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.getId());
        hash = 37 * hash + Objects.hashCode(this.getDataInicioCalculo());
        hash = 37 * hash + Objects.hashCode(this.getDataFinalCalculo());
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
        final PeriodoCalculo other = (PeriodoCalculo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dataInicioCalculo, other.dataInicioCalculo)) {
            return false;
        }
        if (!Objects.equals(this.dataFinalCalculo, other.dataFinalCalculo)) {
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
