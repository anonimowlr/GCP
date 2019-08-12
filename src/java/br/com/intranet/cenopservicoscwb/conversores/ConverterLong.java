/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.conversores;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author PC_LENOVO
 */
@FacesConverter(value = "converterLong")
public class ConverterLong implements Serializable, Converter {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override // da tela para o objeto
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

        try {
            Long retorno = new Long(string);

            return retorno;
        } catch (Exception ex) {

            return null;

        }

    }

    @Override   // do objeto para a tela;
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

        if (o == null) {
            return null;
        }

        Long obj = (Long) o;

        return obj.toString();
    }

}
