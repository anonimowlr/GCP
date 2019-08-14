/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
import br.com.intranet.cenopservicoscwb.model.entidade.Honorario;
import dao.DAOGenerico;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author f5078775
 */
public class TesteHonorario {
    DAOGenerico<Honorario> d = new DAOGenerico<>();
    DAOGenerico<Calculo> d1 = new DAOGenerico<>();
    
    public void iniciar(){
        Honorario honorario = new Honorario();
        
        d1.setClassePersistente(Calculo.class);
        Calculo calculo = d1.buscarObjeto(1);
        
        honorario.setTaxaHonorario(new BigDecimal("0.05"));
        honorario.setValorHonorario(honorario.getTaxaHonorario().multiply(new BigDecimal("1000.00")).setScale(2,RoundingMode.DOWN));        
        
                
        d.salvar(honorario);
    }
}
