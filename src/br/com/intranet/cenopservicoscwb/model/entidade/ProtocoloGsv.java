/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.model.entidade;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author f5078775
 */
@Entity
@Table(name = "tb_protocolo")
public class ProtocoloGsv implements Serializable{
    @Id
    @Column(name = "CD_PRC")
    private Integer cdPrc;
    
    @ManyToOne()
    @JoinColumn(name = "nrPrc")
    private Npj npj;

    /**
     * @return the cdPrc
     */
    public Integer getCdPrc() {
        return cdPrc;
    }

    /**
     * @param cdPrc the cdPrc to set
     */
    public void setCdPrc(Integer cdPrc) {
        this.cdPrc = cdPrc;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.getCdPrc());
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
        final ProtocoloGsv other = (ProtocoloGsv) obj;
        if (!Objects.equals(this.cdPrc, other.cdPrc)) {
            return false;
        }
        return true;
    }

    /**
     * @return the npj
     */
    public Npj getNpj() {
        return npj;
    }

    /**
     * @param npj the npj to set
     */
    public void setNpj(Npj npj) {
        this.npj = npj;
    }
    
    
}
