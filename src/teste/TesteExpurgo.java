/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.Expurgo;
import dao.DAOGenerico;

/**
 *
 * @author f5078775
 */
public class TesteExpurgo {
    DAOGenerico<Expurgo> d = new DAOGenerico<>();
        public void iniciar (){
            Expurgo expurgo = new Expurgo();
            expurgo.setMarcador("N");
            d.salvar(expurgo);
            
            Expurgo expurgo1 = new Expurgo();
            expurgo1.setMarcador("S");
            d.salvar(expurgo1);
        }
}
