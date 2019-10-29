/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
import java.util.List;
import javax.persistence.TypedQuery;

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

    public List<Calculo> consultaCalculoCpf(String cpf) {

         TypedQuery<Calculo> query = em.createQuery(
        "SELECT c FROM Calculo c WHERE c.cliente.cpf = :cpf", classePersistente);
        return query.setParameter("cpf", cpf).getResultList();

    }
    
}
