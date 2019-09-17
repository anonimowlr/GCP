/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


/**
 *
 * @author f5078775
 */
public class FuncionarioDAO<T,E> extends DAOGenerico<Funcionario, Object>{

    public FuncionarioDAO() {
        super();
        classePersistente = Funcionario.class;
        ordem = "id";
        maximoObjeto = 50; 
    }
    
    
    
    
    
    public Funcionario localizarFuncionarioPorChave(String chaveSessao){
        
//        String jpql = "From " + classePersistente + " f where f.chaveFunci = :pChave";
//        
//        Query query = getEm().createQuery(jpql);
//        query.setParameter("pChave", chaveSessao);
//        
//        List<Funcionario> listaFuncionarios = query.getResultList();
//        
//        Funcionario funcionario = listaFuncionarios.get(0);
        
         TypedQuery<Funcionario> query = em.createQuery(
        "SELECT c FROM Funcionario c WHERE c.chaveFunci = :name", classePersistente);
    return query.setParameter("name", chaveSessao).getSingleResult();
        
        
        
        
    }
    
    
}
