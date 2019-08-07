/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.controller;

import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
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

    private FacesContext fc = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

    private Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");

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

    
    
}
