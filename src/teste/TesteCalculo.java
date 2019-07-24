/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
import br.com.intranet.cenopservicoscwb.model.entidade.Npj;
import br.com.intranet.cenopservicoscwb.model.entidade.ProtocoloGsv;
import br.com.intranet.cenopservicoscwb.model.util.Utils;
import dao.DAOGenerico;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;

/**
 *
 * @author f5078775
 */
public class TesteCalculo {

    DAOGenerico<Calculo> d = new DAOGenerico<>();
    DAOGenerico<Funcionario> d2 = new DAOGenerico<>();
    DAOGenerico<Npj> d3 = new DAOGenerico<>();

    public void calcular(Calculo calculo) {
        BigDecimal remuneracaoBasica = calculo.getSaldoBase().multiply(new BigDecimal("0.223591")).setScale(2, RoundingMode.DOWN);
        calculo.setRemuneracaoBasica(remuneracaoBasica);
        BigDecimal jurosCreditado = (calculo.getSaldoBase().add(remuneracaoBasica)).multiply(new BigDecimal("0.005")).setScale(2, RoundingMode.DOWN);
        calculo.setJurosCreditado(jurosCreditado);
        BigDecimal totRendCreditado = remuneracaoBasica.add(jurosCreditado);
        calculo.setTotRendCreditado(totRendCreditado);
        BigDecimal remuneracaoReclamada = calculo.getSaldoBase().multiply(new BigDecimal("0.4272")).setScale(2, RoundingMode.DOWN);
        calculo.setRemuneracaoReclamada(remuneracaoReclamada);
        BigDecimal jurosReclamado = (calculo.getSaldoBase().add(remuneracaoReclamada)).multiply(new BigDecimal("0.005")).setScale(2, RoundingMode.DOWN);
        calculo.setJurosReclamado(jurosReclamado);
        BigDecimal totRendReclamado = remuneracaoReclamada.add(jurosReclamado);
        calculo.setTotRendReclamado(totRendReclamado);
        BigDecimal diferenca = totRendReclamado.subtract(totRendCreditado);

        calculo.setValorDiferenca(diferenca);
    }

    public void iniciar() {
        try {
            d3.setClassePersistente(Npj.class);
            Npj npj = d3.buscarObjeto(new Long("20165558899"));
            ProtocoloGsv protocoloGsv = npj.getListaProtocoloGsv().get(0);
          

            d2.setClassePersistente(Funcionario.class);
            Funcionario funcionario = d2.buscarObjeto(1);

            Calculo calculo = new Calculo();
            calculo.setDataBase(Utils.getDataAtualFormatoMysql());
            calculo.setSaldoBase(new BigDecimal("9371.79"));
            calculo.setNumeroConta(new Long("5874585"));
            calculo.setNumeroAgencia(4587);
            //calculo.setValorDiferenca(new BigDecimal("157.58"));
            calculo.setValorFinal(new BigDecimal("14587.25"));
            calculo.setDataValorFinal(new Date("10/07/2018"));
            calculo.setProtocoloGsv(protocoloGsv);
            calculo.setFuncionario(funcionario);
            calcular(calculo);

            Calculo calculo2 = new Calculo();
            calculo2.setDataBase(Utils.getDataAtualFormatoMysql());
            calculo2.setSaldoBase(new BigDecimal("5487.21"));
            calculo2.setNumeroConta(new Long("5587"));
            calculo2.setNumeroAgencia(3457);
            //calculo2.setValorDiferenca(new BigDecimal("257.98"));
            calculo2.setValorFinal(new BigDecimal("15478.68"));
            calculo2.setDataValorFinal(new Date("01/25/2017"));
            calculo2.setProtocoloGsv(protocoloGsv);
            calculo2.setFuncionario(funcionario);
            calcular(calculo2);

            protocoloGsv.adicionarCalculo(calculo);
            protocoloGsv.adicionarCalculo(calculo2);
            funcionario.adicionarCalculo(calculo);
            funcionario.adicionarCalculo(calculo2);

            d3.atualizar(npj);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
