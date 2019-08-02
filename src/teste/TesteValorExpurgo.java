/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.Expurgo;
import br.com.intranet.cenopservicoscwb.model.entidade.ValorExpurgo;
import dao.DAOGenerico;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author f5078775
 */
public class TesteValorExpurgo {
    DAOGenerico<ValorExpurgo> d = new DAOGenerico<>();
    DAOGenerico<Expurgo> d1 = new DAOGenerico<>();
    
    
    public void iniciar (){
        ValorExpurgo valorExpurgo = new ValorExpurgo();
        
        valorExpurgo.setDataExpurgo(new Date("03/01/1991"));
        valorExpurgo.setIndiceExpurgo(new BigDecimal("0.2187"));
       
        d1.setClassePersistente(Expurgo.class);
        Expurgo expurgo = d1.buscarObjeto(2);
        valorExpurgo.setExpurgo(expurgo);
        d.salvar(valorExpurgo);
    }
}
