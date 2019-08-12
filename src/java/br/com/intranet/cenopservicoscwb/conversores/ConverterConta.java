/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.conversores;

import br.com.intranet.cenopservicoscwb.model.util.Utils;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


/**
 *
 * @author PC_LENOVO
 */
@FacesConverter(value = "converterConta")
public class ConverterConta implements  Serializable,Converter{

   
            
    
    
    
    
    
    
    @Override // da tela para o objeto
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
   
            try{
                Long retorno = Long.parseLong(Utils.tratarVariavel(string));
               
                return  retorno;
            } catch (Exception ex) {
                
                   return  null;
            
            
            }
        
    
    }
 
    @Override   // do objeto para a tela;
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

             if(o== null || o.equals(" ")){
                 return null;
             }
                
             
             Long obj = (Long) o;
             
             String retorno =Utils.tratarConta(o.toString());
             
             
             return  retorno;
    }
    
    
    
}
