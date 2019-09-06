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
@FacesValidator(value = "validaCpf")
public class ValidaCpf implements Validator{

    
    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
       if (value == null) {
            return;
        }
       
        value = Utils.limparPontos((value).toString());
        String cpf = (String) value;
        
//        if(cpf.equals("00000000000")){  // PERMITE CPF ZERADO
//            return;
//        }
        
        if (cpf.length() != 11 || !calcularDigitoVerificador(cpf.substring(0, 9)).equals(cpf.substring(9, 11)) ||cpf.equals("00000000000")|| cpf.equals("11111111111")|| cpf.equals("22222222222")|| cpf.equals("33333333333")|| cpf.equals("44444444444")|| cpf.equals("55555555555")|| cpf.equals("66666666666")|| cpf.equals("77777777777")|| cpf.equals("88888888888")|| cpf.equals("99999999999")) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "CPF inválido.", "Favor informar um CPF válido." ));
        }  
        
        
        
    }
    
    
    private String calcularDigitoVerificador(String num) {


            Integer primDig, segDig;
            int soma = 0;
            int peso = 10;
            for (int i = 0; i < num.length(); i++) {
                soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
            }
            if (soma % 11 == 0 | soma % 11 == 1) {
                primDig = new Integer(0);
            } else {
                primDig = new Integer(11 - (soma % 11));
            }
            soma = 0;
            peso = 11;
            for (int i = 0; i < num.length(); i++) {
                soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
            }
            soma += primDig.intValue() * 2;
            if (soma % 11 == 0 | soma % 11 == 1) {
                segDig = new Integer(0);
            } else {
                segDig = new Integer(11 - (soma % 11));
            }
            return primDig.toString() + segDig.toString();
        }

    
    
}
