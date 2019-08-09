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
@FacesConverter(value = "converterBigInt")
public class ConverterBigInt implements Serializable, Converter {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override // da tela para o objeto
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

        try {
            BigInteger retorno = new BigInteger(string);

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

        BigInteger obj = (BigInteger) o;

        return obj.toString();
    }

}
