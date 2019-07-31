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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author f5078775
 */
@Entity
@Table(name = "tb_arquivo")
public class Arquivo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "NM_ARQUIVO", nullable = false)
    private String nomeArquivo;
    
    @Column(name = "TIPO", nullable = false)
    private String tipoArquivo;
    
    @Column(name = "ENDERECO", nullable = false, length = 400)
    private String enderecoArquivo;
    
    @Column(name = "NPJ", nullable = false)
    private Long npjArquivo;

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
     * @return the nomeArquivo
     */
    public String getNomeArquivo() {
        return nomeArquivo;
    }

    /**
     * @param nomeArquivo the nomeArquivo to set
     */
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    /**
     * @return the tipoArquivo
     */
    public String getTipoArquivo() {
        return tipoArquivo;
    }

    /**
     * @param tipoArquivo the tipoArquivo to set
     */
    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    /**
     * @return the enderecoArquivo
     */
    public String getEnderecoArquivo() {
        return enderecoArquivo;
    }

    /**
     * @param enderecoArquivo the enderecoArquivo to set
     */
    public void setEnderecoArquivo(String enderecoArquivo) {
        this.enderecoArquivo = enderecoArquivo;
    }

    /**
     * @return the npjArquivo
     */
    public Long getNpjArquivo() {
        return npjArquivo;
    }

    /**
     * @param npjArquivo the npjArquivo to set
     */
    public void setNpjArquivo(Long npjArquivo) {
        this.npjArquivo = npjArquivo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.nomeArquivo);
        hash = 37 * hash + Objects.hashCode(this.tipoArquivo);
        hash = 37 * hash + Objects.hashCode(this.enderecoArquivo);
        hash = 37 * hash + Objects.hashCode(this.npjArquivo);
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
        final Arquivo other = (Arquivo) obj;
        if (!Objects.equals(this.nomeArquivo, other.nomeArquivo)) {
            return false;
        }
        if (!Objects.equals(this.tipoArquivo, other.tipoArquivo)) {
            return false;
        }
        if (!Objects.equals(this.enderecoArquivo, other.enderecoArquivo)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.npjArquivo, other.npjArquivo)) {
            return false;
        }
        return true;
    }
    
    
    
}
