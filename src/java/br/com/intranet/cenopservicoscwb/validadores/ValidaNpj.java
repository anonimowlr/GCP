/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.validadores;

import br.com.intranet.cenopservicoscwb.model.util.Utils;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author jocimar
 */
@FacesValidator(value = "validaNpj")
public class ValidaNpj implements Validator{

    
    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
       if (value == null) {
            return;
        }
       
        value = Utils.limparPontos((value).toString());
        String npj = (String) value;
        
        
        if (npj.length() < 14  ) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "NPJ INVÁLIDO.", "Favor informar um NPJ válido." ));
        }  
        
        
        
    }
    
    
    
    
    
}
