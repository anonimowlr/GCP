/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.Indice;
import br.com.intranet.cenopservicoscwb.model.entidade.ValorIndice;
import dao.DAOGenerico;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author f5078775
 */
public class TesteValorIndice {
    
    DAOGenerico<ValorIndice> d = new DAOGenerico<>();
    DAOGenerico<Indice> d1 = new DAOGenerico<>();
    
    public void iniciar(){
        
        d1.setClassePersistente(Indice.class);
        Indice indice = d1.buscarObjeto(1);
        
        
        ValorIndice valorIndice = new ValorIndice();
        valorIndice.setDataValorIndice(new Date("04/05/1992"));
        valorIndice.setValorIndice(new BigDecimal("0.254784"));
        valorIndice.setIndice(indice);
        
        ValorIndice valorIndice2 = new ValorIndice();
        valorIndice2.setDataValorIndice(new Date("06/09/1989"));
        valorIndice2.setValorIndice(new BigDecimal("0.02587"));
        valorIndice2.setIndice(indice);
        
        d.salvar(valorIndice);
        d.salvar(valorIndice2);
    }
    
}
