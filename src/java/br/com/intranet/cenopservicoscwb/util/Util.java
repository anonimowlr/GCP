/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author PC_LENOVO
 */
public class Util {
    
    
    public static String getMensagemErro(Exception e){
            
                while(e.getCause() != null){
                    e = (Exception) e.getCause();
                }
        
                String retorno =  e.getMessage();
                if(retorno.contains("foreign key")){
                    
                    retorno = "Registro nao pode ser excluido, possui referencia no sistema";
                }
                
                return retorno;
    }
    
    
    
    public static void mensagemInformacao(String msg){
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,msg, "");
        facesContext.addMessage(null, facesMessage);
        
    } 
    
    public static void mensagemErro(String msg){
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg, "");
        facesContext.addMessage(null, facesMessage);
        
    } 
    
    
    
    
}
