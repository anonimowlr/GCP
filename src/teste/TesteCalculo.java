/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.intranet.cenopservicoscwb.model.entidade.Arquivo;
import br.com.intranet.cenopservicoscwb.model.entidade.Atualizacao;
import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
import br.com.intranet.cenopservicoscwb.model.entidade.Cliente;
import br.com.intranet.cenopservicoscwb.model.entidade.Expurgo;
import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
import br.com.intranet.cenopservicoscwb.model.entidade.Honorario;
import br.com.intranet.cenopservicoscwb.model.entidade.Indice;
import br.com.intranet.cenopservicoscwb.model.entidade.Metodologia;
import br.com.intranet.cenopservicoscwb.model.entidade.Mora;
import br.com.intranet.cenopservicoscwb.model.entidade.Npj;
import br.com.intranet.cenopservicoscwb.model.entidade.PeriodoCalculo;
import br.com.intranet.cenopservicoscwb.model.entidade.PlanoEconomico;
import br.com.intranet.cenopservicoscwb.model.entidade.ProtocoloGsv;
import br.com.intranet.cenopservicoscwb.model.entidade.ValorExpurgo;
import br.com.intranet.cenopservicoscwb.model.entidade.ValorIndice;
import br.com.intranet.cenopservicoscwb.model.util.Utils;
import dao.DAOGenerico;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author f5078775
 */
public class TesteCalculo {

    DAOGenerico<Calculo> d = new DAOGenerico<>();
    DAOGenerico<Funcionario> d2 = new DAOGenerico<>();
    DAOGenerico<Npj> d3 = new DAOGenerico<>();
    DAOGenerico<Cliente> d4 = new DAOGenerico<>();
    DAOGenerico<Metodologia> d5 = new DAOGenerico<>();
    DAOGenerico<PlanoEconomico> d6 = new DAOGenerico<>();
    DAOGenerico<Indice> d7 = new DAOGenerico<>();
    DAOGenerico<ValorIndice> d8 = new DAOGenerico<>();
    DAOGenerico<Arquivo> d9 = new DAOGenerico<>();
    DAOGenerico<Expurgo> d10 = new DAOGenerico<>();
    DAOGenerico<Mora> d11 = new DAOGenerico<>();
    DAOGenerico<Honorario> d12 = new DAOGenerico<>();

    public void calcular(Calculo calculo) {
        BigDecimal remuneracaoBasica = calculo.getSaldoBase().multiply(calculo.getPlanoEconomico().getIndiceCorrMonPraticado()).setScale(2, RoundingMode.DOWN);
        calculo.setRemuneracaoBasica(remuneracaoBasica);
        BigDecimal jurosCreditado = (calculo.getSaldoBase().add(remuneracaoBasica)).multiply(calculo.getPlanoEconomico().getIndiceJurosRemuneracao()).setScale(2, RoundingMode.DOWN);
        calculo.setJurosCreditado(jurosCreditado);
        BigDecimal totRendCreditado = remuneracaoBasica.add(jurosCreditado);
        calculo.setTotRendCreditado(totRendCreditado);
        BigDecimal remuneracaoReclamada = calculo.getSaldoBase().multiply(calculo.getPlanoEconomico().getIndiceCorrMonReal()).setScale(2, RoundingMode.DOWN);
        calculo.setRemuneracaoReclamada(remuneracaoReclamada);
        BigDecimal jurosReclamado = (calculo.getSaldoBase().add(remuneracaoReclamada)).multiply(calculo.getPlanoEconomico().getIndiceJurosRemuneracao()).setScale(2, RoundingMode.DOWN);
        calculo.setJurosReclamado(jurosReclamado);
        BigDecimal totRendReclamado = remuneracaoReclamada.add(jurosReclamado);
        calculo.setTotRendReclamado(totRendReclamado);
        BigDecimal diferenca = totRendReclamado.subtract(totRendCreditado);

        calculo.setValorDiferenca(diferenca);

    }

    public void atualizar(Calculo calculo) {
        BigDecimal indice = null;
        BigDecimal valorDiferenca = calculo.getValorDiferenca();

        int i = 0;
        for (PeriodoCalculo periodoCalculo : calculo.getListaPeriodoCalculo()) {
            Calendar dataInicial = Calendar.getInstance();
            Calendar dataFinal = Calendar.getInstance();
            dataInicial.setTime(periodoCalculo.getDataInicioCalculo());
            dataFinal.setTime(periodoCalculo.getDataFinalCalculo());

            System.out.println(calculo.getValorDiferenca().toString());

            System.out.println("Mês/Ano: " + "Índice %: " + "Atualização Monetária: " + "Juros: " + "Diferença: ");

            System.out.println(calculo.getListaPeriodoCalculo().get(0).getDataInicioCalculo().getTime() + " " + calculo.getValorDiferenca().toString());

            while (dataInicial.before(dataFinal)) {

                if (i == 0) {
                    System.out.println("===========================================");
                    i++;
                    continue;
                }

                dataInicial.add(dataInicial.MONTH, 1);

                for (ValorIndice valorIndice : periodoCalculo.getIndice().getListaValorIndice()) {
                    indice = null;

                    if (Utils.converteData(valorIndice.getDataValorIndice()).equals(Utils.converteData(dataInicial.getTime()))) {
                        indice = valorIndice.getValorIndice();
                        break;
                    }
                }

                if (indice == null) {

                    i++;
                    dataInicial.add(dataInicial.MONTH, 1);
                    continue;
                }

                Atualizacao atualizacao = new Atualizacao();

                if (calculo.getExpurgo().getMarcador().equals("S")) {

                    for (ValorExpurgo valorExpurgo : calculo.getExpurgo().getListaValorExpurgo()) {

                        if (Utils.converteData(valorExpurgo.getDataExpurgo()).subSequence(3, 10).equals(Utils.converteData(dataInicial.getTime()).subSequence(3, 10))) {
                            indice = valorExpurgo.getIndiceExpurgo();
                            break;

                        }
                    }

                }

                if (dataInicial.get(Calendar.MONTH) == 8 && dataInicial.get(Calendar.YEAR) == 1993) {
                    valorDiferenca = (valorDiferenca.divide(new BigDecimal("1000")).setScale(2, RoundingMode.DOWN));
                    System.out.println(valorDiferenca);
                    
                }


                if (dataInicial.get(Calendar.MONTH) == 7 && dataInicial.get(Calendar.YEAR) == 1994) {
                    valorDiferenca = (valorDiferenca.divide(new BigDecimal("2750"), MathContext.DECIMAL128).setScale(2, RoundingMode.DOWN));

                    System.out.println(calculo.getValorDiferenca());
                }

                atualizacao.setDataApuracao(dataInicial.getTime());
                atualizacao.setFatorAtualizacao(indice);

                atualizacao.setValorAtualizacaoMonetaria(atualizacao.getFatorAtualizacao().multiply(valorDiferenca.divide(new BigDecimal("100"))).setScale(2, RoundingMode.DOWN));
                
                atualizacao.setValorJuros(new BigDecimal("0"));
                atualizacao.setSaldoAtualizadoDiferenca(valorDiferenca);
                
                valorDiferenca = (valorDiferenca.add(atualizacao.getValorAtualizacaoMonetaria()));
                
                System.out.println(valorDiferenca);

                System.out.println(Utils.converteData(atualizacao.getDataApuracao()) + " " + atualizacao.getFatorAtualizacao() + " " + atualizacao.getValorAtualizacaoMonetaria()
                        + " " + atualizacao.getValorJuros() + " " + valorDiferenca);

                System.out.println("=========================================================================================================================");

                
                
                calculo.adicionarAtualizacao(atualizacao);
                calculo.setValorDiferencaAtualizado(atualizacao.getSaldoAtualizadoDiferenca());
                
                i++;

               
           

            }

            
            
            
        }

    }
    
    public void atualizaMoraHonorarioMulta(Calculo calculo) throws ParseException{
        
          
            Mora mora = new Mora();
            mora.setDataInicio(new Date("05/08/2000"));
            mora.setValorMoraPre(new BigDecimal("0.00"));
            calculo.setMora(mora);
           
            
            calcularMora(calculo);
            
            
            
            calculo.setValorAtualizadoComMora(calculo.getValorDiferencaAtualizado().add(mora.getValorMoraPre().add(mora.getValorMoraPos())));
            
            
            Honorario honorario = new Honorario();
            honorario.setTaxaHonorario(new BigDecimal("0.005"));
            honorario.setValorHonorario(honorario.getTaxaHonorario().multiply(calculo.getValorAtualizadoComMora()));
            calculo.setHonorario(honorario);
            
            
            
            
           
            
            calculo.setValorAtualizadoComMora(calculo.getValorDiferencaAtualizado().add(calculo.getMora().getValorMoraPre()).add(calculo.getMora().getValorMoraPos()));
            
            
            
            
            calculo.getListaAtualizacao().clear();
            
            
            
    }
    
    
    

    public void iniciar() {
        try {
            d3.setClassePersistente(Npj.class);
            Npj npj = d3.buscarObjeto(new Long("20165558899"));
            ProtocoloGsv protocoloGsv = npj.getListaProtocoloGsv().get(1);
            protocoloGsv.setNpj(npj);

            d2.setClassePersistente(Funcionario.class);
            Funcionario funcionario = d2.buscarObjeto(1);

            d4.setClassePersistente(Cliente.class);
            Cliente cliente = d4.buscarObjeto(1);

            d5.setClassePersistente(Metodologia.class);
            Metodologia metodologia = d5.buscarObjeto(1);

            d6.setClassePersistente(PlanoEconomico.class);
            PlanoEconomico planoEconomico = d6.buscarObjeto(1);

            d7.setClassePersistente(Indice.class);
            Indice indice = d7.buscarObjeto(1);

            d9.setClassePersistente(Arquivo.class);
            Arquivo arquivo = d9.buscarObjeto(1);

            d10.setClassePersistente(Expurgo.class);
            Expurgo expurgo = d10.buscarObjeto(1);

            //indice.adicionarValorIndice(valorIndice);
            PeriodoCalculo periodo = new PeriodoCalculo();
            periodo.setDataInicioCalculo(new Date("02/08/1989"));
            periodo.setDataFinalCalculo(new Date("08/08/2019"));
            periodo.setIndice(indice);

//            PeriodoCalculo periodo2 = new PeriodoCalculo();
//            periodo2.setDataInicioCalculo(new Date("04/09/1992"));
//            periodo2.setDataFinalCalculo(new Date(Utils.getDataAtual()));
//            periodo2.setIndice(indice);
            Calculo calculo = new Calculo();
            //calculo.setDataBase(Utils.getDataAtualFormatoMysql());
            calculo.setSaldoBase(new BigDecimal("3778.63"));
            calculo.setNumeroConta(new Long("5874585"));
            calculo.setNumeroAgencia(4587);
            //calculo.setValorDiferenca(new BigDecimal("157.58"));
            calculo.setValorFinal(new BigDecimal("11111.11"));
            //calculo.setDataValorFinal(new Date("10/07/2018"));
            calculo.setProtocoloGsv(protocoloGsv);
            calculo.setFuncionario(funcionario);
            calculo.setMetodologia(metodologia);
            calculo.setCliente(cliente);
            calculo.setPlanoEconomico(planoEconomico);
            calculo.adicionarPeriodoCalculo(periodo);
            //calculo.adicionarPeriodoCalculo(periodo2);
            calculo.setDataRealizacaoCalculo(Calendar.getInstance().getTime());
            calculo.adicionarArquivo(arquivo);
            calculo.setExpurgo(expurgo);
            calcular(calculo);

//            Calculo calculo2 = new Calculo();
//            //calculo2.setDataBase(Utils.getDataAtualFormatoMysql());
//            calculo2.setSaldoBase(new BigDecimal("5487.21"));
//            calculo2.setNumeroConta(new Long("5587"));
//            calculo2.setNumeroAgencia(3457);
//            //calculo2.setValorDiferenca(new BigDecimal("257.98"));
//            calculo2.setValorFinal(new BigDecimal("15478.68"));
//            //calculo2.setDataValorFinal(new Date("01/25/2017"));
//            calculo2.setProtocoloGsv(protocoloGsv);
//            calculo2.setFuncionario(funcionario);
//            calculo2.setMetodologia(metodologia);
//            calculo2.setCliente(cliente);
//            calculo2.setPlanoEconomico(planoEconomico);
//            calculo2.adicionarPeriodoCalculo(periodo);
//            calculo2.adicionarPeriodoCalculo(periodo2);
//            calculo2.setDataRealizacaoCalculo(Calendar.getInstance().getTime());
//            calculo2.adicionarArquivo(arquivo);
//            calculo2.setExpurgo(expurgo);
//            calcular(calculo2);
           
           

            
            

            protocoloGsv.adicionarCalculo(calculo);
            //protocoloGsv.adicionarCalculo(calculo2);

            
            atualizar(calculo);
            atualizaMoraHonorarioMulta(calculo);
            d3.atualizar(npj);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void calcularMora(Calculo calculo) throws ParseException {
        
        int qtdMesPre= 0;
        int qtdMesPos= 0;
        
       
        
       
        
      
        Date dataInicioPos = calculo.getMora().getDataInicio();
        
        
        
        if(calculo.getMora().getDataInicio().before(new Date ("01/10/2003"))){
             qtdMesPre = Utils.diferencaDataMes2(calculo.getMora().getDataInicio(), new Date("01/10/2003"));
             calculo.getMora().setValorMoraPre((new BigDecimal(Integer.toString(qtdMesPre)).multiply(new BigDecimal("0.005"))).multiply(calculo.getValorDiferencaAtualizado()).setScale(2, RoundingMode.DOWN));
              System.out.println(calculo.getValorDiferencaAtualizado());
        
               System.out.println(calculo.getMora().getValorMoraPre().toString());
             
            dataInicioPos = new Date("01/10/2003");
            
        }
        
           
        
        
        
             qtdMesPos = Utils.diferencaDataMes2(dataInicioPos, calculo.getListaPeriodoCalculo().get(calculo.getListaPeriodoCalculo().size()-1).getDataFinalCalculo());
             calculo.getMora().setValorMoraPos((new BigDecimal(Integer.toString(qtdMesPos)).multiply(new BigDecimal("0.01"))).multiply(calculo.getValorDiferencaAtualizado()).setScale(2, RoundingMode.DOWN));
             
            
        
        
        
        
        
        





    }
}
