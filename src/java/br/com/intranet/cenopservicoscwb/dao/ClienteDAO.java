/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.model.entidade.Cliente;
import javax.persistence.TypedQuery;

/**
 *
 * @author f5078775
 */
public class ClienteDAO<T, E> extends DAOGenerico<Cliente, Object> {

    public ClienteDAO() {
        super();
        classePersistente = Cliente.class;
        ordem = "id";
        maximoObjeto = 100000;

    }

    public Cliente localizarCliente(String cpf) {
        try {
            
        TypedQuery<Cliente> query = em.createQuery(
                "SELECT c FROM Cliente c WHERE c.cpf = :cpf", classePersistente);
        return query.setParameter("cpf", cpf).getSingleResult();
        } catch (Exception e) {
            return null;
        }
        

    }
}
