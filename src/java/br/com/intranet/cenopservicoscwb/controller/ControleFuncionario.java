/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.controller;

import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author f5078775
 */
@ManagedBean
@SessionScoped
public class ControleFuncionario {
    
    
    
    
    
    
    private boolean  cargoGerencial = false;

    private FacesContext fc = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

    private Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");
    
    
    @PostConstruct
    public void init(){
        mudarParaCargoGerencial();
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

    
    
    public void mudarParaCargoGerencial(){
        if(usuario.getCodigoFuncao()== 4665 || usuario.getCodigoFuncao()== 4750 || usuario.getCodigoFuncao()== 7001){
            this.cargoGerencial=true;
            
        }
        
    }
    
    
}
