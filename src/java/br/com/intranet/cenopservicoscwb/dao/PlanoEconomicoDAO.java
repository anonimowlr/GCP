/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.model.entidade.PlanoEconomico;

/**
 *
 * @author f5078775
 */
public class PlanoEconomicoDAO<T, E> extends DAOGenerico<PlanoEconomico, Object>{

    public PlanoEconomicoDAO() {
        super();
        classePersistente = PlanoEconomico.class;
        ordem = "id";
        maximoObjeto = 50; 
    }
    
}
