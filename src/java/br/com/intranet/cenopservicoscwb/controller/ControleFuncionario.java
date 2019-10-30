/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.controller;

import br.com.intranet.cenopservicoscwb.dao.FuncionarioDAO;
import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author f5078775
 */
@ManagedBean
@ViewScoped
public class ControleFuncionario {
  
    private boolean cargoGerencial = false;
    private boolean funcionarioComAcesso = false;
    private FuncionarioDAO<Funcionario, Object> funcionarioDAO;

    private FacesContext fc = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

    private Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");    

    @PostConstruct
    public void init() {
        funcionarioDAO = new FuncionarioDAO<>();
        mudarParaCargoGerencial();
        verificarAcessoFuncionario();
    }

    public void mudarParaCargoGerencial() {
        if (getUsuario().getCodigoFuncao() == 4665 || getUsuario().getCodigoFuncao() == 4750 || getUsuario().getMatriculaFunci()== 5078775 || getUsuario().getMatriculaFunci() == 7864599) {
            this.setCargoGerencial(true);
        }
    }
    
    public void verificarAcessoFuncionario(){
        List<Funcionario> funcionario = getFuncionarioDAO().localizarFuncionarioPorChaveLista(usuario.getChaveFunci());
        if(funcionario.size() > 0){
            this.setFuncionarioComAcesso(true);
        }
    }

    /**
     * @return the usuario
     */
    public Funcionario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Funcionario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the cargoGerencial
     */
    public boolean isCargoGerencial() {
        return cargoGerencial;
    }

    /**
     * @param cargoGerencial the cargoGerencial to set
     */
    public void setCargoGerencial(boolean cargoGerencial) {
        this.cargoGerencial = cargoGerencial;
    }

    /**
     * @return the fc
     */
    public FacesContext getFc() {
        return fc;
    }

    /**
     * @param fc the fc to set
     */
    public void setFc(FacesContext fc) {
        this.fc = fc;
    }

    /**
     * @return the session
     */
    public HttpSession getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(HttpSession session) {
        this.session = session;
    }

    /**
     * @return the funcionarioComAcesso
     */
    public boolean isFuncionarioComAcesso() {
        return funcionarioComAcesso;
    }

    /**
     * @param funcionarioComAcesso the funcionarioComAcesso to set
     */
    public void setFuncionarioComAcesso(boolean funcionarioComAcesso) {
        this.funcionarioComAcesso = funcionarioComAcesso;
    }

        /**
     * @return the funcionarioDAO
     */
    public FuncionarioDAO<Funcionario, Object> getFuncionarioDAO() {
        return funcionarioDAO;
    }

    /**
     * @param funcionarioDAO the funcionarioDAO to set
     */
    public void setFuncionarioDAO(FuncionarioDAO<Funcionario, Object> funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

}
