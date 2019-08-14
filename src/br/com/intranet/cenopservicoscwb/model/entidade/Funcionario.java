/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.model.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "tb_funci")
public class Funcionario implements Serializable{
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "MATRICULA", nullable = false, unique = true)
    private Integer matriculaFunci;
    
    @Column(name = "CHAVE", nullable = false, unique = true)
    private String chaveFunci;
    
    @Column(name = "NM_FUNCI", nullable = false)
    private String nomeFunci;
    
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calculo> listaCalculo = new ArrayList<>();
    
    public void adicionarCalculo(Calculo calculo){
        calculo.setFuncionario(this);
        getListaCalculo().add(calculo);
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
     * @return the matriculaFunci
     */
    public Integer getMatriculaFunci() {
        return matriculaFunci;
    }

    /**
     * @param matriculaFunci the matriculaFunci to set
     */
    public void setMatriculaFunci(Integer matriculaFunci) {
        this.matriculaFunci = matriculaFunci;
    }

    /**
     * @return the chaveFunci
     */
    public String getChaveFunci() {
        return chaveFunci;
    }

    /**
     * @param chaveFunci the chaveFunci to set
     */
    public void setChaveFunci(String chaveFunci) {
        this.chaveFunci = chaveFunci;
    }

    /**
     * @return the nomeFunci
     */
    public String getNomeFunci() {
        return nomeFunci;
    }

    /**
     * @param nomeFunci the nomeFunci to set
     */
    public void setNomeFunci(String nomeFunci) {
        this.nomeFunci = nomeFunci;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.getId());
        hash = 73 * hash + Objects.hashCode(this.getMatriculaFunci());
        hash = 73 * hash + Objects.hashCode(this.getChaveFunci());
        hash = 73 * hash + Objects.hashCode(this.getNomeFunci());
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
        final Funcionario other = (Funcionario) obj;
        if (!Objects.equals(this.chaveFunci, other.chaveFunci)) {
            return false;
        }
        if (!Objects.equals(this.nomeFunci, other.nomeFunci)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.matriculaFunci, other.matriculaFunci)) {
            return false;
        }
        return true;
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
    
    
}
