/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.Npj;
import br.com.intranet.cenopservicoscwb.model.entidade.ProtocoloGsv;
import dao.DAOGenerico;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author f5078775
 */
public class Teste {
    static DAOGenerico d = new DAOGenerico();
    
    public static void main(String[] args) {
        List<Npj> listaNpj = new ArrayList<>();
        listaNpj = d.buscar();
        
        for (Npj npj : listaNpj) {
            d.apagar(npj);
        }

        try {
        Npj npj = new Npj();
        npj.setNrPrc(new Long("20165558899"));
        
        ProtocoloGsv p1 = new ProtocoloGsv();
        p1.setCdPrc(87458915);
        npj.adicionarProtocolo(p1);
        
        ProtocoloGsv p2 = new ProtocoloGsv();
        p2.setCdPrc(48745689);
        npj.adicionarProtocolo(p2);
        
//        d.atualizar(npj);
        d.salvar(npj);
        
        } catch (Exception e) {
            System.out.println(e);
        }        
    }
}
