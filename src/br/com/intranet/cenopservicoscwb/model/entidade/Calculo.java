/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
    
    @Column(name = "NM_BCO")
    private String nomeBanco;
    
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
    
    @Column(name = "DT_RLZC_CALCULO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRealizacaoCalculo;
    
    @Column(name = "VLR_DIF_ATLZD", nullable = false)
    private BigDecimal valorDiferencaAtualizado;
    
    @Column(name = "VLR_ATLZD_C_MORA", nullable = false)
    private BigDecimal valorAtualizadoComMora;
        
    
    
        
    @ManyToOne
    @JoinColumn(name = "CD_PRC", referencedColumnName = "CD_PRC")
    private ProtocoloGsv protocoloGsv;
    
    @ManyToOne
    @JoinColumn(name = "CD_FUNCI", referencedColumnName = "id")
    private Funcionario funcionario;
    
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CLI", referencedColumnName = "id")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "CD_METODOLOGIA", referencedColumnName = "id")
    private Metodologia metodologia;
    
    @ManyToOne
    @JoinColumn(name = "CD_PLANO_ECONOMICO", referencedColumnName = "id")
    private PlanoEconomico planoEconomico;
    
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "tb_calculo_has_tb_periodo_calculo", joinColumns = 
//    {@JoinColumn(name = "ID_CALCULO", referencedColumnName = "id")},
//    inverseJoinColumns = 
//    {@JoinColumn(name = "ID_PERIODO", referencedColumnName = "id")})
//    private List<PeriodoCalculo> listaPeriodoCalculo = new ArrayList<>();
//    
    
    
     @OneToMany(mappedBy = "calculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
     private List<PeriodoCalculo> listaPeriodoCalculo = new ArrayList<>();
    
    
    @OneToMany(mappedBy = "calculo", cascade = CascadeType.ALL)
    private List<Atualizacao> listaAtualizacao = new ArrayList<>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_calculo_has_tb_arquivo", joinColumns = 
    {@JoinColumn(name = "CD_CALCULO", referencedColumnName = "id")},
    inverseJoinColumns = 
    {@JoinColumn(name = "CD_ARQUIVO", referencedColumnName = "id")})
    private List<Arquivo> listaArquivo = new ArrayList<>();
    
    @OneToOne
    @JoinColumn(name = "EXPURGO")
    private Expurgo expurgo;    
   
     
    @OneToOne(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MORA")
    private Mora mora;
    
    @OneToOne(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_HONORARIO")
    private Honorario honorario;
    
    @OneToOne(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MULTA")
    private Multa multa;
    
    
    
    public void adicionarAtualizacao(Atualizacao atualizacao){
        atualizacao.setCalculo(this);
        getListaAtualizacao().add(atualizacao);
    }
    
    public void adicionarPeriodoCalculo(PeriodoCalculo periodoCalculo){
        periodoCalculo.setCalculo(this);
        getListaPeriodoCalculo().add(periodoCalculo);
    }
    
    public void adicionarArquivo(Arquivo arquivo){
        getListaArquivo().add(arquivo);
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
     * @return the nomeBanco
     */
    public String getNomeBanco() {
        return nomeBanco;
    }

    /**
     * @param nomeBanco the nomeBanco to set
     */
    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
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
     * @return the dataRealizacaoCalculo
     */
    public Date getDataRealizacaoCalculo() {
        return dataRealizacaoCalculo;
    }

    /**
     * @param dataRealizacaoCalculo the dataRealizacaoCalculo to set
     */
    public void setDataRealizacaoCalculo(Date dataRealizacaoCalculo) {
        this.dataRealizacaoCalculo = dataRealizacaoCalculo;
    }

    /**
     * @return the valorDiferencaAtualizado
     */
    public BigDecimal getValorDiferencaAtualizado() {
        return valorDiferencaAtualizado;
    }

    /**
     * @param valorDiferencaAtualizado the valorDiferencaAtualizado to set
     */
    public void setValorDiferencaAtualizado(BigDecimal valorDiferencaAtualizado) {
        this.valorDiferencaAtualizado = valorDiferencaAtualizado;
    }

    /**
     * @return the valorAtualizadoComMora
     */
    public BigDecimal getValorAtualizadoComMora() {
        return valorAtualizadoComMora;
    }

    /**
     * @param valorAtualizadoComMora the valorAtualizadoComMora to set
     */
    public void setValorAtualizadoComMora(BigDecimal valorAtualizadoComMora) {
        this.valorAtualizadoComMora = valorAtualizadoComMora;
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

    /**
     * @return the listaAtualizacao
     */
    public List<Atualizacao> getListaAtualizacao() {
        return listaAtualizacao;
    }

    /**
     * @param listaAtualizacao the listaAtualizacao to set
     */
    public void setListaAtualizacao(List<Atualizacao> listaAtualizacao) {
        this.listaAtualizacao = listaAtualizacao;
    }

    /**
     * @return the listaArquivo
     */
    public List<Arquivo> getListaArquivo() {
        return listaArquivo;
    }

    /**
     * @param listaArquivo the listaArquivo to set
     */
    public void setListaArquivo(List<Arquivo> listaArquivo) {
        this.listaArquivo = listaArquivo;
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

    /**
     * @return the mora
     */
    public Mora getMora() {
        return mora;
    }

    /**
     * @param mora the mora to set
     */
    public void setMora(Mora mora) {
        this.mora = mora;
    }

    /**
     * @return the honorario
     */
    public Honorario getHonorario() {
        return honorario;
    }

    /**
     * @param honorario the honorario to set
     */
    public void setHonorario(Honorario honorario) {
        this.honorario = honorario;
    }

    /**
     * @return the multa
     */
    public Multa getMulta() {
        return multa;
    }

    /**
     * @param multa the multa to set
     */
    public void setMulta(Multa multa) {
        this.multa = multa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.saldoBase);
        hash = 41 * hash + Objects.hashCode(this.numeroConta);
        hash = 41 * hash + Objects.hashCode(this.numeroAgencia);
        hash = 41 * hash + Objects.hashCode(this.nomeBanco);
        hash = 41 * hash + Objects.hashCode(this.valorDiferenca);
        hash = 41 * hash + Objects.hashCode(this.valorFinal);
        hash = 41 * hash + Objects.hashCode(this.remuneracaoBasica);
        hash = 41 * hash + Objects.hashCode(this.jurosCreditado);
        hash = 41 * hash + Objects.hashCode(this.totRendCreditado);
        hash = 41 * hash + Objects.hashCode(this.remuneracaoReclamada);
        hash = 41 * hash + Objects.hashCode(this.jurosReclamado);
        hash = 41 * hash + Objects.hashCode(this.totRendReclamado);
        hash = 41 * hash + Objects.hashCode(this.dataRealizacaoCalculo);
        hash = 41 * hash + Objects.hashCode(this.valorDiferencaAtualizado);
        hash = 41 * hash + Objects.hashCode(this.valorAtualizadoComMora);
        hash = 41 * hash + Objects.hashCode(this.protocoloGsv);
        hash = 41 * hash + Objects.hashCode(this.funcionario);
        hash = 41 * hash + Objects.hashCode(this.cliente);
        hash = 41 * hash + Objects.hashCode(this.metodologia);
        hash = 41 * hash + Objects.hashCode(this.planoEconomico);
        hash = 41 * hash + Objects.hashCode(this.listaPeriodoCalculo);
        hash = 41 * hash + Objects.hashCode(this.listaAtualizacao);
        hash = 41 * hash + Objects.hashCode(this.listaArquivo);
        hash = 41 * hash + Objects.hashCode(this.expurgo);
        hash = 41 * hash + Objects.hashCode(this.mora);
        hash = 41 * hash + Objects.hashCode(this.honorario);
        hash = 41 * hash + Objects.hashCode(this.multa);
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
        if (!Objects.equals(this.nomeBanco, other.nomeBanco)) {
            return false;
        }
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
        if (!Objects.equals(this.dataRealizacaoCalculo, other.dataRealizacaoCalculo)) {
            return false;
        }
        if (!Objects.equals(this.valorDiferencaAtualizado, other.valorDiferencaAtualizado)) {
            return false;
        }
        if (!Objects.equals(this.valorAtualizadoComMora, other.valorAtualizadoComMora)) {
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
        if (!Objects.equals(this.listaAtualizacao, other.listaAtualizacao)) {
            return false;
        }
        if (!Objects.equals(this.listaArquivo, other.listaArquivo)) {
            return false;
        }
        if (!Objects.equals(this.expurgo, other.expurgo)) {
            return false;
        }
        if (!Objects.equals(this.mora, other.mora)) {
            return false;
        }
        if (!Objects.equals(this.honorario, other.honorario)) {
            return false;
        }
        if (!Objects.equals(this.multa, other.multa)) {
            return false;
        }
        return true;
    }

    
    
    
    
   
}
