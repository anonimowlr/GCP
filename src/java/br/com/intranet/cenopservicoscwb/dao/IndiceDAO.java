/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.model.entidade.Indice;

/**
 *
 * @author f5078775
 */
public class IndiceDAO<T, E> extends DAOGenerico<Indice, Object>{

    public IndiceDAO() {
        super();
        classePersistente = Indice.class;
        ordem = "id";
        maximoObjeto = 50; 
        em.clear();
    }
        
}
