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
    static DAOGenerico<Npj> d = new DAOGenerico<>();
   
    static DAOGenerico<ProtocoloGsv> d1 = new DAOGenerico<>();
    
    public static void main(String[] args) {
//        List<Npj> listaNpj = new ArrayList<>();
//        d.setClassePersistente(Npj.class);
//       
//        try {
//            
//        listaNpj = d.buscar();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        
//        for (Npj npj : listaNpj) {
//            d.apagar(npj);
//        }

        try {
        Npj npj = new Npj();
        npj.setNrPrc(new Long("205445454"));
        
        ProtocoloGsv p1 = new ProtocoloGsv();
        p1.setCdPrc(87458915);
        npj.adicionarProtocolo(p1);
        
        ProtocoloGsv p2 = new ProtocoloGsv();
        p2.setCdPrc(48745689);
        npj.adicionarProtocolo(p2);
        
        d.atualizar(npj);
//        d.salvar(npj);
        
        } catch (Exception e) {
            System.out.println(e);
        }        
    }
}
