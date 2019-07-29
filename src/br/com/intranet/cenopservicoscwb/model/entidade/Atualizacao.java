package br.com.intranet.cenopservicoscwb.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author f5078775
 */
@Entity
public class Atualizacao implements Serializable{
    @Id
    private Integer id;
    @Temporal(TemporalType.DATE)
    private Date dataApuracao;
    private BigDecimal fatorAtualizacao;
    private BigDecimal valorAtualizacaoMonetaria;
    private BigDecimal valorJuros;
    private BigDecimal saldoAtualizadoDiferenca;
    
    
    @ManyToOne
    private Calculo calculo;
    
    

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
     * @return the dataApuracao
     */
    public Date getDataApuracao() {
        return dataApuracao;
    }

    /**
     * @param dataApuracao the dataApuracao to set
     */
    public void setDataApuracao(Date dataApuracao) {
        this.dataApuracao = dataApuracao;
    }

    /**
     * @return the fatorAtualizacao
     */
    public BigDecimal getFatorAtualizacao() {
        return fatorAtualizacao;
    }

    /**
     * @param fatorAtualizacao the fatorAtualizacao to set
     */
    public void setFatorAtualizacao(BigDecimal fatorAtualizacao) {
        this.fatorAtualizacao = fatorAtualizacao;
    }

    /**
     * @return the valorAtualizacaoMonetaria
     */
    public BigDecimal getValorAtualizacaoMonetaria() {
        return valorAtualizacaoMonetaria;
    }

    /**
     * @param valorAtualizacaoMonetaria the valorAtualizacaoMonetaria to set
     */
    public void setValorAtualizacaoMonetaria(BigDecimal valorAtualizacaoMonetaria) {
        this.valorAtualizacaoMonetaria = valorAtualizacaoMonetaria;
    }

    /**
     * @return the valorJuros
     */
    public BigDecimal getValorJuros() {
        return valorJuros;
    }

    /**
     * @param valorJuros the valorJuros to set
     */
    public void setValorJuros(BigDecimal valorJuros) {
        this.valorJuros = valorJuros;
    }

    /**
     * @return the saldoAtualizadoDiferenca
     */
    public BigDecimal getSaldoAtualizadoDiferenca() {
        return saldoAtualizadoDiferenca;
    }

    /**
     * @param saldoAtualizadoDiferenca the saldoAtualizadoDiferenca to set
     */
    public void setSaldoAtualizadoDiferenca(BigDecimal saldoAtualizadoDiferenca) {
        this.saldoAtualizadoDiferenca = saldoAtualizadoDiferenca;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.getId());
        hash = 53 * hash + Objects.hashCode(this.getDataApuracao());
        hash = 53 * hash + Objects.hashCode(this.getFatorAtualizacao());
        hash = 53 * hash + Objects.hashCode(this.getValorAtualizacaoMonetaria());
        hash = 53 * hash + Objects.hashCode(this.getValorJuros());
        hash = 53 * hash + Objects.hashCode(this.getSaldoAtualizadoDiferenca());
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
        final Atualizacao other = (Atualizacao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dataApuracao, other.dataApuracao)) {
            return false;
        }
        if (!Objects.equals(this.fatorAtualizacao, other.fatorAtualizacao)) {
            return false;
        }
        if (!Objects.equals(this.valorAtualizacaoMonetaria, other.valorAtualizacaoMonetaria)) {
            return false;
        }
        if (!Objects.equals(this.valorJuros, other.valorJuros)) {
            return false;
        }
        if (!Objects.equals(this.saldoAtualizadoDiferenca, other.saldoAtualizadoDiferenca)) {
            return false;
        }
        return true;
    }

    /**
     * @return the calculo
     */
    public Calculo getCalculo() {
        return calculo;
    }

    /**
     * @param calculo the calculo to set
     */
    public void setCalculo(Calculo calculo) {
        this.calculo = calculo;
    }
    
}
