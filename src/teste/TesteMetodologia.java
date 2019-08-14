/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.Metodologia;
import dao.DAOGenerico;

/**
 *
 * @author f5078775
 */
public class TesteMetodologia {
    
    DAOGenerico<Object> d = new DAOGenerico<>();
        
    public void iniciar (){
        Metodologia metodologia = new Metodologia();
        metodologia.setNomeMetodologia("Pessoa Física - PF");
        
        d.salvar(metodologia);
    }
    
}
