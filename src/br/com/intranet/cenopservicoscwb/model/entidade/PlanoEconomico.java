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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author f5078775
 */
@Entity
@Table(name = "tb_plano_economico")
public class PlanoEconomico implements Serializable{
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "NM_PLANO", nullable = false, unique = true)
    private String nomePlanoEconomico;
    
    @Column(name = "INDICE_CORR_MON_PRATICADO", scale = 6, precision = 6, nullable = false)
    private BigDecimal indiceCorrMonPraticado;
    
    @Column(name = "INDICE_CORR_MON_REAL", scale = 6, precision = 6, nullable = false)
    private BigDecimal indiceCorrMonReal;
    
    @Column(name = "INDICE_JUROS_RMNC", scale = 6, precision = 6, nullable = false)
    private BigDecimal indiceJurosRemuneracao;
    
    @OneToMany(mappedBy = "planoEconomico", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Calculo> listaCalculo = new ArrayList<>();

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
     * @return the nomePlanoEconomico
     */
    public String getNomePlanoEconomico() {
        return nomePlanoEconomico;
    }

    /**
     * @param nomePlanoEconomico the nomePlanoEconomico to set
     */
    public void setNomePlanoEconomico(String nomePlanoEconomico) {
        this.nomePlanoEconomico = nomePlanoEconomico;
    }

    /**
     * @return the indiceCorrMonPraticado
     */
    public BigDecimal getIndiceCorrMonPraticado() {
        return indiceCorrMonPraticado;
    }

    /**
     * @param indiceCorrMonPraticado the indiceCorrMonPraticado to set
     */
    public void setIndiceCorrMonPraticado(BigDecimal indiceCorrMonPraticado) {
        this.indiceCorrMonPraticado = indiceCorrMonPraticado;
    }

    /**
     * @return the indiceCorrMonReal
     */
    public BigDecimal getIndiceCorrMonReal() {
        return indiceCorrMonReal;
    }

    /**
     * @param indiceCorrMonReal the indiceCorrMonReal to set
     */
    public void setIndiceCorrMonReal(BigDecimal indiceCorrMonReal) {
        this.indiceCorrMonReal = indiceCorrMonReal;
    }

    /**
     * @return the indiceJurosRemuneracao
     */
    public BigDecimal getIndiceJurosRemuneracao() {
        return indiceJurosRemuneracao;
    }

    /**
     * @param indiceJurosRemuneracao the indiceJurosRemuneracao to set
     */
    public void setIndiceJurosRemuneracao(BigDecimal indiceJurosRemuneracao) {
        this.indiceJurosRemuneracao = indiceJurosRemuneracao;
    }

    /**
     * @return the listaCalculo
     */
    public List<Calculo> getListaCalculo() {
        return listaCalculo;
    }

    /**
     * @param listaCalculo the listaCalculo to set
     */
    public void setListaCalculo(List<Calculo> listaCalculo) {
        this.listaCalculo = listaCalculo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.nomePlanoEconomico);
        hash = 37 * hash + Objects.hashCode(this.indiceCorrMonPraticado);
        hash = 37 * hash + Objects.hashCode(this.indiceCorrMonReal);
        hash = 37 * hash + Objects.hashCode(this.indiceJurosRemuneracao);
        hash = 37 * hash + Objects.hashCode(this.listaCalculo);
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
        final PlanoEconomico other = (PlanoEconomico) obj;
        if (!Objects.equals(this.nomePlanoEconomico, other.nomePlanoEconomico)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.indiceCorrMonPraticado, other.indiceCorrMonPraticado)) {
            return false;
        }
        if (!Objects.equals(this.indiceCorrMonReal, other.indiceCorrMonReal)) {
            return false;
        }
        if (!Objects.equals(this.indiceJurosRemuneracao, other.indiceJurosRemuneracao)) {
            return false;
        }
        if (!Objects.equals(this.listaCalculo, other.listaCalculo)) {
            return false;
        }
        return true;
    }
    
  
    
}
