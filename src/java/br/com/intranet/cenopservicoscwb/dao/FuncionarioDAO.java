/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;

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
    
}
