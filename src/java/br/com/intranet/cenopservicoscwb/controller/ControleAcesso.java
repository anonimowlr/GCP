/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.controller;

import br.com.intranet.cenopservicoscwb.dao.FuncionarioDAO;
import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
import br.com.intranet.cenopservicoscwb.model.util.Utils;
import br.com.intranet.cenopservicoscwb.util.Util;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author f5078775
 */
@ManagedBean
@ViewScoped
public class ControleAcesso {

    private FuncionarioDAO<Funcionario, Object> funcionarioDAO;
    private Funcionario funcionario;
    private String estadoTela = "buscar";

    @PostConstruct
    public void init() {
        novo();
    }

    public ControleAcesso() {
        funcionarioDAO = new FuncionarioDAO<>();
    }

    public void novo() {
        setFuncionario(new Funcionario());
    }

    public boolean isBuscar() {
        return "buscar".equals(estadoTela);
    }

    public void mudarParaBuscar() {
        setEstadoTela("buscar");
    }

    public void mudarParaEditar() {
        novo();
        setEstadoTela("editar");
    }

    public boolean isEditar() {
        return "editar".equals(estadoTela);
    }

    public void salvar() {
        getFuncionarioDAO().salvar(getFuncionario());
       
    }

    public void excluir(Funcionario funcionario) {
        if (getFuncionarioDAO().deletar(funcionario)) {
            Util.mensagemInformacao(getFuncionarioDAO().getMensagem());
        } else {
            Util.mensagemErro(getFuncionarioDAO().getMensagem());
        }
    }

    public void complementar() {

        if (getFuncionario().getNomeFunci().equals("")) {
            Util.mensagemErro("Campo nome não pode ser  vazio");
            return;
        }
        
        getFuncionario().setChaveFunci(getFuncionario().getChaveFunci().toUpperCase());
        getFuncionario().setNomeFunci(getFuncionario().getNomeFunci().toUpperCase());
        
        

        try {

            getFuncionario().setMatriculaFunci(Integer.parseInt(Utils.tratarVariavel(getFuncionario().getChaveFunci())));
            salvar();
           mudarParaBuscar();

        } catch (Exception e) {
            
            Util.mensagemErro(Util.getMensagemErro(e));
        }
        
        
        

    }

    /**
     * @return the funcionarioDAO
     */
    public FuncionarioDAO getFuncionarioDAO() {
        return funcionarioDAO;
    }

    /**
     * @param funcionarioDAO the funcionarioDAO to set
     */
    public void setFuncionarioDAO(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
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
     * @return the estadoTela
     */
    public String getEstadoTela() {
        return estadoTela;
    }

    /**
     * @param estadoTela the estadoTela to set
     */
    public void setEstadoTela(String estadoTela) {
        this.estadoTela = estadoTela;
    }

}
