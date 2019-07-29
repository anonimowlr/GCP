/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    
    @ManyToOne
    @JoinColumn(name = "CD_CLI", referencedColumnName = "id")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "CD_METODOLOGIA", referencedColumnName = "id")
    private Metodologia metodologia;
    
    @ManyToOne
    @JoinColumn(name = "CD_PLANO_ECONOMICO", referencedColumnName = "id")
    private PlanoEconomico planoEconomico;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_calculo_has_periodo_calculo", joinColumns = 
    {@JoinColumn(name = "ID_CALCULO", referencedColumnName = "id")},
    inverseJoinColumns = 
    {@JoinColumn(name = "ID_PERIDO", referencedColumnName = "id")})
    private List<PeriodoCalculo> listaPeriodoCalculo = new ArrayList<>();
    
    @OneToMany(mappedBy = "calculo", cascade = CascadeType.ALL)
    private List<Atualizacao> listaAtualizacao = new ArrayList<>();
    
    public void adicionarAtualizacao(Atualizacao atualizacao){
        atualizacao.setCalculo(this);
        listaAtualizacao.add(atualizacao);
    }
    
    public void adicionarPeriodoCalculo(PeriodoCalculo periodoCalculo){
        getListaPeriodoCalculo().add(periodoCalculo);
    }

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

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the metodologia
     */
    public Metodologia getMetodologia() {
        return metodologia;
    }

    /**
     * @param metodologia the metodologia to set
     */
    public void setMetodologia(Metodologia metodologia) {
        this.metodologia = metodologia;
    }

    /**
     * @return the planoEconomico
     */
    public PlanoEconomico getPlanoEconomico() {
        return planoEconomico;
    }

    /**
     * @param planoEconomico the planoEconomico to set
     */
    public void setPlanoEconomico(PlanoEconomico planoEconomico) {
        this.planoEconomico = planoEconomico;
    }

    /**
     * @return the listaPeriodoCalculo
     */
    public List<PeriodoCalculo> getListaPeriodoCalculo() {
        return listaPeriodoCalculo;
    }

    /**
     * @param listaPeriodoCalculo the listaPeriodoCalculo to set
     */
    public void setListaPeriodoCalculo(List<PeriodoCalculo> listaPeriodoCalculo) {
        this.listaPeriodoCalculo = listaPeriodoCalculo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.saldoBase);
        hash = 59 * hash + Objects.hashCode(this.numeroConta);
        hash = 59 * hash + Objects.hashCode(this.numeroAgencia);
        hash = 59 * hash + Objects.hashCode(this.valorDiferenca);
        hash = 59 * hash + Objects.hashCode(this.valorFinal);
        hash = 59 * hash + Objects.hashCode(this.remuneracaoBasica);
        hash = 59 * hash + Objects.hashCode(this.jurosCreditado);
        hash = 59 * hash + Objects.hashCode(this.totRendCreditado);
        hash = 59 * hash + Objects.hashCode(this.remuneracaoReclamada);
        hash = 59 * hash + Objects.hashCode(this.jurosReclamado);
        hash = 59 * hash + Objects.hashCode(this.totRendReclamado);
        hash = 59 * hash + Objects.hashCode(this.protocoloGsv);
        hash = 59 * hash + Objects.hashCode(this.funcionario);
        hash = 59 * hash + Objects.hashCode(this.cliente);
        hash = 59 * hash + Objects.hashCode(this.metodologia);
        hash = 59 * hash + Objects.hashCode(this.planoEconomico);
        hash = 59 * hash + Objects.hashCode(this.listaPeriodoCalculo);
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
        if (!Objects.equals(this.remuneracaoBasica, other.remuneracaoBasica)) {
            return false;
        }
        if (!Objects.equals(this.jurosCreditado, other.jurosCreditado)) {
            return false;
        }
        if (!Objects.equals(this.totRendCreditado, other.totRendCreditado)) {
            return false;
        }
        if (!Objects.equals(this.remuneracaoReclamada, other.remuneracaoReclamada)) {
            return false;
        }
        if (!Objects.equals(this.jurosReclamado, other.jurosReclamado)) {
            return false;
        }
        if (!Objects.equals(this.totRendReclamado, other.totRendReclamado)) {
            return false;
        }
        if (!Objects.equals(this.protocoloGsv, other.protocoloGsv)) {
            return false;
        }
        if (!Objects.equals(this.funcionario, other.funcionario)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.metodologia, other.metodologia)) {
            return false;
        }
        if (!Objects.equals(this.planoEconomico, other.planoEconomico)) {
            return false;
        }
        if (!Objects.equals(this.listaPeriodoCalculo, other.listaPeriodoCalculo)) {
            return false;
        }
        return true;
    }
    

   
    
}
