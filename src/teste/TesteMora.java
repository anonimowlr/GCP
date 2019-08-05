/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
import br.com.intranet.cenopservicoscwb.model.entidade.Mora;
import dao.DAOGenerico;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author f5078775
 */
public class TesteMora {
    DAOGenerico<Mora> d = new DAOGenerico<>();
    DAOGenerico<Calculo> d1 = new DAOGenerico<>();
    
    public void iniciar(){        
        d1.setClassePersistente(Calculo.class);
        Calculo calculo = d1.buscarObjeto(2);
        Mora mora = new Mora();
        
        mora.setDataInicio(new Date("05/03/1989"));
        mora.setValorMoraPre(new BigDecimal("1000.00"));
        mora.setValorMoraPos(new BigDecimal("1000.00"));
        
        d.salvar(mora);
    }
}
