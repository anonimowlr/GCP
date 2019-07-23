/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.model.entidade;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author f5078775
 */
@Entity
@Table(name = "tb_npj")
public class Npj implements Serializable{
    @Id
    @Column(name = "NR_PRC")
    private Long nrPrc;
    
    @OneToMany(mappedBy = "npj", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProtocoloGsv> listaProtocoloGsv = new ArrayList<>();

    public void adicionarProtocolo (ProtocoloGsv protocoloGsv){
        protocoloGsv.setNpj(this);
        getListaProtocoloGsv().add(protocoloGsv);
    }

    /**
     * @return the nrPrc
     */
    public Long getNrPrc() {
        return nrPrc;
    }

    /**
     * @param nrPrc the nrPrc to set
     */
    public void setNrPrc(Long nrPrc) {
        this.nrPrc = nrPrc;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.getNrPrc());
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
        final Npj other = (Npj) obj;
        if (!Objects.equals(this.nrPrc, other.nrPrc)) {
            return false;
        }
        return true;
    }

    /**
     * @return the listaProtocoloGsv
     */
    public List<ProtocoloGsv> getListaProtocoloGsv() {
        return listaProtocoloGsv;
    }

    /**
     * @param listaProtocoloGsv the listaProtocoloGsv to set
     */
    public void setListaProtocoloGsv(List<ProtocoloGsv> listaProtocoloGsv) {
        this.listaProtocoloGsv = listaProtocoloGsv;
    }
}
