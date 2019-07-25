/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.Indice;
import dao.DAOGenerico;

/**
 *
 * @author f5078775
 */
public class TesteIndice {
    
    DAOGenerico<Indice> d = new DAOGenerico<>();
    
    public void iniciar (){
        Indice indice = new Indice();
        indice.setCodigoIndice(2587);
        indice.setNomeIndice("alfa");
        
        d.salvar(indice);
    }       
}
