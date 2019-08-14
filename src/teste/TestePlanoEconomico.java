/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.PlanoEconomico;
import dao.DAOGenerico;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author f5078775
 */
public class TestePlanoEconomico {
    
    DAOGenerico<PlanoEconomico> d = new DAOGenerico<>();
    
    public void iniciar () {
        
        PlanoEconomico planoEconomico = new PlanoEconomico();
        planoEconomico.setNomePlanoEconomico("Plano Verão");
        planoEconomico.setIndiceCorrMonPraticado(new BigDecimal("0.223591"));
        planoEconomico.setIndiceCorrMonReal(new BigDecimal("0.4272"));
        planoEconomico.setIndiceJurosRemuneracao(new BigDecimal("0.005"));
        
        d.salvar(planoEconomico);
        
        PlanoEconomico planoEconomico2 = new PlanoEconomico();
        planoEconomico2.setNomePlanoEconomico("Plano Bresser");
        planoEconomico2.setIndiceCorrMonPraticado(new BigDecimal("0.180205"));
        planoEconomico2.setIndiceCorrMonReal(new BigDecimal("0.2606"));
        planoEconomico2.setIndiceJurosRemuneracao(new BigDecimal("0.005"));
        
        d.salvar(planoEconomico2);
    }
}
