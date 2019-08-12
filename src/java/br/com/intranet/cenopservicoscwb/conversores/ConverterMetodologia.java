/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.conversores;

import br.com.intranet.cenopservicoscwb.model.entidade.Metodologia;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jpa.EntityManagerUtil;

/**
 *
 * @author PC_LENOVO
 */
@FacesConverter(value = "converterMetodologia")
public class ConverterMetodologia implements Serializable, Converter {


    @Override // da tela para o objeto
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

       if(string == null || string.equals("selecione")){
           return null;
           
       }
       
       return EntityManagerUtil.getEntityManager().find(Metodologia.class, Integer.parseInt(string));

    }

    @Override   // do objeto para a tela;
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

        if (o == null) {
            return null;
        }

        Metodologia obj = (Metodologia) o;

        return obj.getId().toString();
    }

}
