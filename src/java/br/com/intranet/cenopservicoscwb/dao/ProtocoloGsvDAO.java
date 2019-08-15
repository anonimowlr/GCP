/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
import br.com.intranet.cenopservicoscwb.model.entidade.ProtocoloGsv;

/**
 *
 * @author f5078775
 */
public class ProtocoloGsvDAO<T, E> extends DAOGenerico<ProtocoloGsv, Object>{

    public ProtocoloGsvDAO() {
        super();
        
        classePersistente = ProtocoloGsv.class;
        ordem = "cdPrc";
        maximoObjeto = 50;
        em.clear();
        
    }
    
}
