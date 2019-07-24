/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author f5078775
 */
@Entity
@Table (name = "tb_calculo")
public class Calculo implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "DT_BASE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataBase;
    
    @Column(name = "SD_BASE", nullable = false)
    private BigDecimal saldoBase;
    
    @Column(name = "NR_CTA", nullable = false)
    private Long numeroConta;
    
    @Column(name = "NR_AGE")
    private Integer numeroAgencia;
    
    @Column(name = "VLR_DIF", nullable = false)
    private BigDecimal valorDiferenca;
    
    @Column(name = "VLR_FINAL", nullable = false)    
    private BigDecimal valorFinal;
    
    @Column(name = "DT_VLR_FINAL", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataValorFinal;
    
    @Column(name = "RMNC_BSCA_CRED", nullable = false)
    private BigDecimal remuneracaoBasica;
    
    @Column(name = "JR_CRED", nullable = false)
    private BigDecimal jurosCreditado;
    
    @Column(name = "TOT_RNDTO_CRED", nullable = false)
    private BigDecimal totRendCreditado;
    
    @Column(name = "RMNC_BSCA_RCLMD", nullable = false)
    private BigDecimal remuneracaoReclamada;
    
    @Column(name = "JR_RCLMD", nullable = false)
    private BigDecimal jurosReclamado;
    
    @Column(name = "TOT_RNDTO_RCLMD", nullable = false)
    private BigDecimal totRendReclamado;
    
    
    
    @ManyToOne
    @JoinColumn(name = "CD_PRC", referencedColumnName = "CD_PRC")
    private ProtocoloGsv protocoloGsv;
    
    @ManyToOne
    @JoinColumn(name = "CD_FUNCI", referencedColumnName = "id")
    private Funcionario funcionario;
    

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
     * @return the dataBase
     */
    public Date getDataBase() {
        return dataBase;
    }

    /**
     * @param dataBase the dataBase to set
     */
    public void setDataBase(Date dataBase) {
        this.dataBase = dataBase;
    }

    /**
     * @return the saldoBase
     */
    public BigDecimal getSaldoBase() {
        return saldoBase;
    }

    /**
     * @param saldoBase the saldoBase to set
     */
    public void setSaldoBase(BigDecimal saldoBase) {
        this.saldoBase = saldoBase;
    }

    /**
     * @return the numeroConta
     */
    public Long getNumeroConta() {
        return numeroConta;
    }

    /**
     * @param numeroConta the numeroConta to set
     */
    public void setNumeroConta(Long numeroConta) {
        this.numeroConta = numeroConta;
    }

    /**
     * @return the numeroAgencia
     */
    public Integer getNumeroAgencia() {
        return numeroAgencia;
    }

    /**
     * @param numeroAgencia the numeroAgencia to set
     */
    public void setNumeroAgencia(Integer numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }

    /**
     * @return the valorDiferenca
     */
    public BigDecimal getValorDiferenca() {
        return valorDiferenca;
    }

    /**
     * @param valorDiferenca the valorDiferenca to set
     */
    public void setValorDiferenca(BigDecimal valorDiferenca) {
        this.valorDiferenca = valorDiferenca;
    }

    /**
     * @return the valorFinal
     */
    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    /**
     * @param valorFinal the valorFinal to set
     */
    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    /**
     * @return the dataValorFinal
     */
    public Date getDataValorFinal() {
        return dataValorFinal;
    }

    /**
     * @param dataValorFinal the dataValorFinal to set
     */
    public void setDataValorFinal(Date dataValorFinal) {
        this.dataValorFinal = dataValorFinal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.getId());
        hash = 37 * hash + Objects.hashCode(this.getDataBase());
        hash = 37 * hash + Objects.hashCode(this.getSaldoBase());
        hash = 37 * hash + Objects.hashCode(this.getNumeroConta());
        hash = 37 * hash + Objects.hashCode(this.getNumeroAgencia());
        hash = 37 * hash + Objects.hashCode(this.getValorDiferenca());
        hash = 37 * hash + Objects.hashCode(this.getValorFinal());
        hash = 37 * hash + Objects.hashCode(this.getDataValorFinal());
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
        final Calculo other = (Calculo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dataBase, other.dataBase)) {
            return false;
        }
        if (!Objects.equals(this.saldoBase, other.saldoBase)) {
            return false;
        }
        if (!Objects.equals(this.numeroConta, other.numeroConta)) {
            return false;
        }
        if (!Objects.equals(this.numeroAgencia, other.numeroAgencia)) {
            return false;
        }
        if (!Objects.equals(this.valorDiferenca, other.valorDiferenca)) {
            return false;
        }
        if (!Objects.equals(this.valorFinal, other.valorFinal)) {
            return false;
        }
        if (!Objects.equals(this.dataValorFinal, other.dataValorFinal)) {
            return false;
        }
        return true;
    }

    /**
     * @return the protocoloGsv
     */
    public ProtocoloGsv getProtocoloGsv() {
        return protocoloGsv;
    }

    /**
     * @param protocoloGsv the protocoloGsv to set
     */
    public void setProtocoloGsv(ProtocoloGsv protocoloGsv) {
        this.protocoloGsv = protocoloGsv;
    }

    /**
     * @return the remuneracaoBasica
     */
    public BigDecimal getRemuneracaoBasica() {
        return remuneracaoBasica;
    }

    /**
     * @param remuneracaoBasica the remuneracaoBasica to set
     */
    public void setRemuneracaoBasica(BigDecimal remuneracaoBasica) {
        this.remuneracaoBasica = remuneracaoBasica;
    }

    /**
     * @return the jurosCreditado
     */
    public BigDecimal getJurosCreditado() {
        return jurosCreditado;
    }

    /**
     * @param jurosCreditado the jurosCreditado to set
     */
    public void setJurosCreditado(BigDecimal jurosCreditado) {
        this.jurosCreditado = jurosCreditado;
    }

    /**
     * @return the totRendCreditado
     */
    public BigDecimal getTotRendCreditado() {
        return totRendCreditado;
    }

    /**
     * @param totRendCreditado the totRendCreditado to set
     */
    public void setTotRendCreditado(BigDecimal totRendCreditado) {
        this.totRendCreditado = totRendCreditado;
    }

    /**
     * @return the remuneracaoReclamada
     */
    public BigDecimal getRemuneracaoReclamada() {
        return remuneracaoReclamada;
    }

    /**
     * @param remuneracaoReclamada the remuneracaoReclamada to set
     */
    public void setRemuneracaoReclamada(BigDecimal remuneracaoReclamada) {
        this.remuneracaoReclamada = remuneracaoReclamada;
    }

    /**
     * @return the jurosReclamado
     */
    public BigDecimal getJurosReclamado() {
        return jurosReclamado;
    }

    /**
     * @param jurosReclamado the jurosReclamado to set
     */
    public void setJurosReclamado(BigDecimal jurosReclamado) {
        this.jurosReclamado = jurosReclamado;
    }

    /**
     * @return the totRendReclamado
     */
    public BigDecimal getTotRendReclamado() {
        return totRendReclamado;
    }

    /**
     * @param totRendReclamado the totRendReclamado to set
     */
    public void setTotRendReclamado(BigDecimal totRendReclamado) {
        this.totRendReclamado = totRendReclamado;
    }

    /**
     * @return the funcionario
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    
}
