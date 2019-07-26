/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.PeriodoCalculo;
import br.com.intranet.cenopservicoscwb.model.util.Utils;
import dao.DAOGenerico;
import java.util.Date;

/**
 *
 * @author f5078775
 */
public class TestePeriodo {
    
    DAOGenerico<PeriodoCalculo> d = new DAOGenerico<>();
    
    public void iniciar (){
        
        PeriodoCalculo periodoCalculo = new PeriodoCalculo();
        periodoCalculo.setDataInicioCalculo(new Date("05/05/1993"));
        periodoCalculo.setDataFinalCalculo(new Date(Utils.getDataAtual()));
        
        PeriodoCalculo periodoCalculo2 = new PeriodoCalculo();
        periodoCalculo2.setDataInicioCalculo(new Date("04/01/1989"));
        periodoCalculo2.setDataFinalCalculo(new Date(Utils.getDataAtual()));
        
        d.salvar(periodoCalculo);
        d.salvar(periodoCalculo2);
        
    }
    
}
