/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
import dao.DAOGenerico;

/**
 *
 * @author f5078775
 */
public class TesteFuncionario {
    
    DAOGenerico<Funcionario> d = new DAOGenerico();
    
    public void iniciar (){
        Funcionario funcionario = new Funcionario();
        funcionario.setChaveFunci("f7864599");
        funcionario.setMatriculaFunci(7864599);
        funcionario.setNomeFunci("Oscar");
        
        d.salvar(funcionario);
    }
}
