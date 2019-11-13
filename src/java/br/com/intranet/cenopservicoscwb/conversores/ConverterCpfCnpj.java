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
@FacesConverter(value = "converterCpfCnpj")
public class ConverterCpfCnpj implements  Serializable,Converter{
    
    @Override // da tela para o objeto
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
   
        try {
            String retorno = verificarCpfCnpj(Utils.limparPontos(string));
            return retorno;
        } catch (Exception ex) {
            return null;
        }
        
    }
 
    @Override   // do objeto para a tela;
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

        if (o == null || o.equals(" ")) {
            return null;
        }
        String obj =  (String) o;
        String retorno = (String) o;
        return retorno;
    }  
    
    
    
    
    public String verificarCpfCnpj(String string){
        
        
        if(string.length()>11){
           return imprimeCNPJ(string);
        } else{
          return imprimeCPF(string);  
        }
        
      
    }
    
    
    
    
     public static String imprimeCNPJ(String CNPJ) {
// máscara do CNPJ: 99.999.999.9999-99
    return(CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." +
      CNPJ.substring(5, 8) + "/" + CNPJ.substring(8, 12) + "-" +
      CNPJ.substring(12, 14));
  }
    
      public static String imprimeCPF(String CPF) {
// máscara do CNPJ: 999.999.999.99
    return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
      CPF.substring(6, 9) + "-" + CPF.substring(9, 11) );
  }
    
}
