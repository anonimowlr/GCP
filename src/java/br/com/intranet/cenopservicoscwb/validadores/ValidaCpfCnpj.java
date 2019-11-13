/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.validadores;

import br.com.intranet.cenopservicoscwb.model.util.Utils;
import java.util.InputMismatchException;
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
@FacesValidator(value = "validaCpfCnpj")
public class ValidaCpfCnpj implements Validator{

    
    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
       if (value == null) {
            return;
        }
       
        value = Utils.limparPontos((value).toString());
        String cpfCnpj = (String) value;
        
        if(cpfCnpj.length()>11 && !isCNPJ(cpfCnpj)){
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "CPF/CNPJ inválido.", "Favor informar um CPF/CNPJ válido."));
            
        } else if(cpfCnpj.length() <=11){
             if (cpfCnpj.length() != 11 || !calcularDigitoVerificador(cpfCnpj.substring(0, 9)).equals(cpfCnpj.substring(9, 11)) ||cpfCnpj.equals("00000000000")|| cpfCnpj.equals("11111111111")|| cpfCnpj.equals("22222222222")|| cpfCnpj.equals("33333333333")|| cpfCnpj.equals("44444444444")|| cpfCnpj.equals("55555555555")|| cpfCnpj.equals("66666666666")|| cpfCnpj.equals("77777777777")|| cpfCnpj.equals("88888888888")|| cpfCnpj.equals("99999999999")) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "CPF/CNPJ inválido.", "Favor informar um CPF/CNPJ válido." ));
        }  
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

    
    
      public static boolean isCNPJ(String CNPJ) {
// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
    if ( CNPJ.equals("11111111111111") ||CNPJ.equals("00000000000000") ||
        CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
        CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
        CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
        CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
       (CNPJ.length() != 14))
       return(false);
 
    char dig13, dig14;
    int sm, i, r, num, peso;
 
// "try" - protege o código para eventuais erros de conversao de tipo (int)
    try {
// Calculo do 1o. Digito Verificador
      sm = 0;
      peso = 2;
      for (i=11; i>=0; i--) {
// converte o i-ésimo caractere do CNPJ em um número:
// por exemplo, transforma o caractere '0' no inteiro 0
// (48 eh a posição de '0' na tabela ASCII)
        num = (int)(CNPJ.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso + 1;
        if (peso == 10)
           peso = 2;
      }
 
      r = sm % 11;
      if ((r == 0) || (r == 1))
         dig13 = '0';
      else dig13 = (char)((11-r) + 48);
 
// Calculo do 2o. Digito Verificador
      sm = 0;
      peso = 2;
      for (i=12; i>=0; i--) {
        num = (int)(CNPJ.charAt(i)- 48);
        sm = sm + (num * peso);
        peso = peso + 1;
        if (peso == 10)
           peso = 2;
      }
 
      r = sm % 11;
      if ((r == 0) || (r == 1))
         dig14 = '0';
      else dig14 = (char)((11-r) + 48);
 
// Verifica se os dígitos calculados conferem com os dígitos informados.
      if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
         return(true);
      else return(false);
    } catch (InputMismatchException erro) {
        return(false);
    }
  }
    
    
    
}
