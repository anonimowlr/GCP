/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.model.entidade.Cliente;
import br.com.intranet.cenopservicoscwb.model.entidade.Metodologia;

/**
 *
 * @author f5078775
 */
public class ClienteDAO<T, E> extends DAOGenerico<Cliente, Object>{
    
    public ClienteDAO() {
        super();
        classePersistente = Cliente.class;
        ordem = "id";
        maximoObjeto = 50;        
    }
}
