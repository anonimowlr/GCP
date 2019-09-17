/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.model.entidade.Metodologia;

/**
 *
 * @author f5078775
 */
public class MetodologiaDAO<T, E> extends DAOGenerico<Metodologia, Object>{
    
    public MetodologiaDAO() {
        super();
        classePersistente = Metodologia.class;
        ordem = "id";
        maximoObjeto = 50;   
    }
}
