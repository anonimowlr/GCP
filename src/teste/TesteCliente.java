/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.Cliente;
import dao.DAOGenerico;

/**
 *
 * @author f5078775
 */
public class TesteCliente {
    
    DAOGenerico<Cliente> d = new DAOGenerico<>();
      
    public void iniciar (){
        Cliente cliente = new Cliente();
        cliente.setCpf("111.258.786-89");
        cliente.setNomeCliente("Jose da Silva");
        
        d.salvar(cliente);
    }
}
