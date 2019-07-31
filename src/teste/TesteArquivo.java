/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.Arquivo;
import dao.DAOGenerico;

/**
 *
 * @author f5078775
 */
public class TesteArquivo {
    DAOGenerico d = new DAOGenerico();
    
    public void iniciar(){
        Arquivo arquivo = new Arquivo();
        arquivo.setNomeArquivo("1254125412");
        arquivo.setTipoArquivo(".pdf");
        arquivo.setEnderecoArquivo("/etc/lib/adhklk");
        arquivo.setNpjArquivo(new Long("20185261458"));
        
        d.salvar(arquivo);
    }
}
