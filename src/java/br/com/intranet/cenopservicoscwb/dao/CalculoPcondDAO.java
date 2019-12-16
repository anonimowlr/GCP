/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
import br.com.intranet.cenopservicoscwb.model.entidade.CalculoPcond;
import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author f5078775
 */
public class CalculoPcondDAO<T, E> extends DAOGenerico<CalculoPcond, Object>{

    public CalculoPcondDAO() {
        super();
        
        classePersistente = CalculoPcond.class;
        ordem = "id";
        maximoObjeto = 1000;
        em.clear();
    }
    
    
    
    
     public List<CalculoPcond> localizarCalculoPcondPorProtocolo(Integer protocolo) {

        TypedQuery<CalculoPcond> query = em.createQuery(
        "SELECT c FROM CalculoPcond c WHERE c.protocoloGsv.cdPrc = :name", classePersistente);
        return query.setParameter("name", protocolo).getResultList();
    }

    
}
