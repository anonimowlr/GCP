/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;

/**
 *
 * @author f5078775
 */
public class CalculoDAO<T, E> extends DAOGenerico<Calculo, Object>{

    public CalculoDAO() {
        super();
        
        classePersistente = Calculo.class;
        ordem = "id";
        maximoObjeto = 1000;
        em.clear();
    }
    
}
