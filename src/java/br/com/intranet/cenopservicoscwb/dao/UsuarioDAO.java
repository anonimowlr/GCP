package br.com.intranet.cenopservicoscwb.dao;;
import br.com.intranet.cenopservicoscwb.conexao.ConnectionFactory;
import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
import br.com.intranet.cenopservicoscwb.model.util.Utils;
import br.com.intranet.cenopservicoscwb.util.ErroSistema;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UsuarioDAO {
    
   public Funcionario getFuncionario(String chave, String uor, String nomeFuncionario) throws ClassNotFoundException, SQLException, ErroSistema {
       
        Connection con = ConnectionFactory.conectar("calc_poupanca");
        Statement busca = con.createStatement();
        String sql = "SELECT A.*,B.cd_area,C.Quadro, spf_dados.nm_gerente_funci(A.matriculaF, '4750') AS gerente FROM arh.v_funcionarios A " +
                        "LEFT JOIN gestao_equipes.tb_uor_area B " +
                        "ON A.UOR_Posicao=B.uor " +
                        "LEFT JOIN spf_dados.tb_arh_base C " +
                        "ON A.matriculaF = C.tx_mtc_fun " +
                        "WHERE A.matriculaF=TRIM('" + chave + "')";

        ResultSet tabela = busca.executeQuery(sql);

              Funcionario funci = new Funcionario();

        try {
            if (tabela.next()) {
                funci.setChaveFunci(chave);
                funci.setNomeFunci(tabela.getString("nome_guerra"));
                funci.setMatriculaFunci(Integer.parseInt(Utils.tratarVariavel(chave)));
                
//                funci.setChave(chave);
//                funci.setNome(nomeFuncionario.toUpperCase());
//                funci.setNomeGuerra(tabela.getString("nome_guerra"));
//                funci.setFuncao(Integer.parseInt(tabela.getString("funcao")));
//                funci.setNomeFuncao(tabela.getString("nFuncao"));
//                funci.setUORHabitual(Integer.parseInt(uor));
//                funci.setUORPosicao(Integer.parseInt(uor));
//                funci.setGerente(tabela.getString("gerente"));
//                funci.setSecao(tabela.getString("secao"));
//                funci.setQuadro(tabela.getString("Quadro"));
            } else{
               funci.setChaveFunci(chave);
                funci.setNomeFunci(nomeFuncionario);
                funci.setMatriculaFunci(Integer.parseInt(Utils.tratarVariavel(chave)));
                
            }
            tabela.close();
        } catch (SQLException e) {
        } finally{
            try{tabela.close();} catch(SQLException e){}
            try{busca.close();} catch(SQLException e){}
            try{con.close();} catch(SQLException e){}
        }
        
        
              
    

//    funci.setChave("F5078775");
//    funci.setNome("funci teste nao continuar");
//    funci.setFuncao(4750);
//    funci.setUORHabitual(286409);
//    funci.setUORPosicao(286409);

    return funci;
    }
}
