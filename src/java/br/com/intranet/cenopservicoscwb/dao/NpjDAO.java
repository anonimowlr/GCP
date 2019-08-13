/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.model.entidade.Npj;

/**
 *
 * @author f5078775
 */
public class NpjDAO<T, E> extends DAOGenerico<Npj, Object>{

    public NpjDAO() {
        super();
        
        classePersistente = Npj.class;
        ordem = "nrPrc";
        maximoObjeto = 50;
    }
    
}
