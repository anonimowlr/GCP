/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.controller;

import br.com.intranet.cenopservicoscwb.dao.CalculoDAO;
import br.com.intranet.cenopservicoscwb.dao.ClienteDAO;
import br.com.intranet.cenopservicoscwb.dao.ExpurgoDAO;
import br.com.intranet.cenopservicoscwb.dao.FuncionarioDAO;
import br.com.intranet.cenopservicoscwb.dao.IndiceDAO;
import br.com.intranet.cenopservicoscwb.dao.MetodologiaDAO;
import br.com.intranet.cenopservicoscwb.dao.NpjDAO;
import br.com.intranet.cenopservicoscwb.dao.PlanoEconomicoDAO;
import br.com.intranet.cenopservicoscwb.dao.ProtocoloGsvDAO;
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
import br.com.intranet.cenopservicoscwb.model.entidade.Multa;
import br.com.intranet.cenopservicoscwb.model.entidade.Npj;
import br.com.intranet.cenopservicoscwb.model.entidade.PeriodoCalculo;
import br.com.intranet.cenopservicoscwb.model.entidade.PlanoEconomico;
import br.com.intranet.cenopservicoscwb.model.entidade.ProtocoloGsv;
import br.com.intranet.cenopservicoscwb.util.Util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author f5078775
 */
@ManagedBean
@ViewScoped
public class ControleCalculo {

    private CalculoDAO<Calculo, Object> calculoDAO;
    private ExpurgoDAO<Expurgo, Object> expurgoDAO;
    private ProtocoloGsvDAO<ProtocoloGsv, Object> protocoloGsvDAO;
    private NpjDAO<Npj, Object> npjDAO;
    private PlanoEconomicoDAO<PlanoEconomico, Object> planoEconomicoDAO;
    private MetodologiaDAO<Metodologia, Object> metodologiaDAO;
    private IndiceDAO<Indice, Object> indiceDAO;
    private FuncionarioDAO<Funcionario, Object> funcionarioDAO;
    private ClienteDAO<Cliente, Object> clienteDAO;
    private Npj npj;
    private ProtocoloGsv protocoloGsv;
    private Calculo calculo;
    private Cliente cliente;
    private Expurgo expurgo;
    private Metodologia metodologia;
    private Funcionario funcionario;
    private PlanoEconomico planoEconomico;
    private Multa multa;
    private Honorario honorario;
    private Mora mora;
    private Arquivo arquivo;
    private Atualizacao atualizacao;
    private PeriodoCalculo periodoCalculo;

    public ControleCalculo() {
        npj = new Npj();
        protocoloGsv = new ProtocoloGsv();
        npjDAO = new NpjDAO<>();
        calculoDAO = new CalculoDAO<>();
        expurgoDAO = new ExpurgoDAO<>();
        protocoloGsvDAO = new ProtocoloGsvDAO<>();
        planoEconomicoDAO = new PlanoEconomicoDAO<>();
        metodologiaDAO = new MetodologiaDAO<>();
        indiceDAO = new IndiceDAO<>();
        funcionarioDAO = new FuncionarioDAO<>();
        clienteDAO = new ClienteDAO<>();
        
    }

    public void novo() {
        
       
        

        try {

            Npj npj = getNpjDAO().localizar(getNpj().getNrPrc());
            ProtocoloGsv protocoloGsv = getProtocoloGsvDAO().localizar(getProtocoloGsv().getCdPrc());

            if (npj != null) {
                setNpj(npj);
            }

            if (protocoloGsv != null) {
                setProtocoloGsv(protocoloGsv);
                setCalculo(new Calculo());
                getProtocoloGsv().adicionarCalculo(getCalculo());

            } else {
                getNpj().adicionarProtocolo(getProtocoloGsv());
                setCalculo(new Calculo());
                getProtocoloGsv().adicionarCalculo(getCalculo());

            }

//            setCalculo(new Calculo());
//            getProtocoloGsv().adicionarCalculo(getCalculo());

        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));
        }

    }

    public void duplicar() {

        setCalculo(new Calculo());
        getNpj().adicionarProtocolo(getProtocoloGsv());
        getProtocoloGsv().adicionarCalculo(getCalculo());
    }

    public void salvar() {

        if(getNpjDAO().atualizar(getNpj())){
            
        Util.mensagemInformacao(getNpjDAO().getMensagem());
        } else{
            
        Util.mensagemErro(getNpjDAO().getMensagem());
        }
        


    }
    
    
    public void removeLinhaCalculo(Calculo calculo){
        getCalculoDAO().deletar(calculo);
        calculo.getProtocoloGsv().getListaCalculo().remove(calculo);
        
    }
    
    
    public void teste(){
        Util.mensagemInformacao("Desenvolver o método");
    }

    public void calcular() {

        try {
            
            for (Calculo  c : getProtocoloGsv().getListaCalculo()) {
                setCalculo(c);
            
            
            
            
            getCalculo().setDataRealizacaoCalculo(new Date("08/09/2019"));
            getCalculo().setJurosCreditado(new BigDecimal("1524.00"));
            getCalculo().setJurosReclamado(new BigDecimal("2564.00"));
            getCalculo().setRemuneracaoBasica(new BigDecimal("456.00"));
            getCalculo().setRemuneracaoReclamada(new BigDecimal("780.00"));
            getCalculo().setTotRendCreditado(new BigDecimal("55444.00"));
            getCalculo().setTotRendReclamado(new BigDecimal("78500.00"));
            getCalculo().setValorAtualizadoComMora(new BigDecimal("458746.00"));
            getCalculo().setValorDiferenca(new BigDecimal("456.00"));
            getCalculo().setValorDiferencaAtualizado(new BigDecimal("45678.00"));
            getCalculo().setValorFinal(new BigDecimal("555555.00"));

            Cliente cliente = getClienteDAO().localizarCliente("111.222.888-77");
            if (cliente == null) {
                cliente = new Cliente();
                cliente.setCpf("111.222.888-77");
                cliente.setNomeCliente("Jose");
                getClienteDAO().salvar(cliente);
            }

            getCalculo().setCliente(cliente);




            Multa multa = new Multa();
            multa.setTaxaMulta(new BigDecimal("0.05"));
            multa.setValorMulta(new BigDecimal("50.00"));
            getCalculo().setMulta(multa);

            Honorario honorario = new Honorario();
            honorario.setTaxaHonorario(new BigDecimal("0.10"));
            honorario.setValorHonorario(new BigDecimal("100.00"));
            getCalculo().setHonorario(honorario);

            Mora mora = new Mora();
            mora.setDataInicio(new java.util.Date("05/25/2000"));
            mora.setValorMoraPre(new BigDecimal("150.00"));
            mora.setValorMoraPos(new BigDecimal("120.00"));
            getCalculo().setMora(mora);

            Indice indice = getIndiceDAO().localizar(1);
            PeriodoCalculo periodoCalculo = new PeriodoCalculo();
            periodoCalculo.setDataInicioCalculo(new java.util.Date("04/08/1989"));
            periodoCalculo.setDataFinalCalculo(new java.util.Date("08/09/2019"));
            periodoCalculo.setIndice(indice);
            getCalculo().adicionarPeriodoCalculo(periodoCalculo);

            Arquivo arquivo = new Arquivo();
            arquivo.setEnderecoArquivo("/qqcoisa");
            arquivo.setNomeArquivo("arquivoX");
            arquivo.setNpjArquivo(new Long("555555555"));
            arquivo.setTipoArquivo(".pdf");
            getCalculo().adicionarArquivo(arquivo);

            Funcionario funcionario = getFuncionarioDAO().localizar(1);
            getCalculo().setFuncionario(funcionario);

            salvar();
            }

        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));
        }

    }

    /**
     * @return the calculoDAO
     */
    public CalculoDAO<Calculo, Object> getCalculoDAO() {
        return calculoDAO;
    }

    /**
     * @param calculoDAO the calculoDAO to set
     */
    public void setCalculoDAO(CalculoDAO<Calculo, Object> calculoDAO) {
        this.calculoDAO = calculoDAO;
    }

    /**
     * @return the npjDAO
     */
    public NpjDAO<Npj, Object> getNpjDAO() {
        return npjDAO;
    }

    /**
     * @param npjDAO the npjDAO to set
     */
    public void setNpjDAO(NpjDAO<Npj, Object> npjDAO) {
        this.npjDAO = npjDAO;
    }

    /**
     * @return the planoEconomicoDAO
     */
    public PlanoEconomicoDAO<PlanoEconomico, Object> getPlanoEconomicoDAO() {
        return planoEconomicoDAO;
    }

    /**
     * @param planoEconomicoDAO the planoEconomicoDAO to set
     */
    public void setPlanoEconomicoDAO(PlanoEconomicoDAO<PlanoEconomico, Object> planoEconomicoDAO) {
        this.planoEconomicoDAO = planoEconomicoDAO;
    }

    /**
     * @return the metodologiaDAO
     */
    public MetodologiaDAO<Metodologia, Object> getMetodologiaDAO() {
        return metodologiaDAO;
    }

    /**
     * @param metodologiaDAO the metodologiaDAO to set
     */
    public void setMetodologiaDAO(MetodologiaDAO<Metodologia, Object> metodologiaDAO) {
        this.metodologiaDAO = metodologiaDAO;
    }

    /**
     * @return the indiceDAO
     */
    public IndiceDAO<Indice, Object> getIndiceDAO() {
        return indiceDAO;
    }

    /**
     * @param indiceDAO the indiceDAO to set
     */
    public void setIndiceDAO(IndiceDAO<Indice, Object> indiceDAO) {
        this.indiceDAO = indiceDAO;
    }

    /**
     * @return the funcionarioDAO
     */
    public FuncionarioDAO<Funcionario, Object> getFuncionarioDAO() {
        return funcionarioDAO;
    }

    /**
     * @param funcionarioDAO the funcionarioDAO to set
     */
    public void setFuncionarioDAO(FuncionarioDAO<Funcionario, Object> funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

    /**
     * @return the clienteDAO
     */
    public ClienteDAO<Cliente, Object> getClienteDAO() {
        return clienteDAO;
    }

    /**
     * @param clienteDAO the clienteDAO to set
     */
    public void setClienteDAO(ClienteDAO<Cliente, Object> clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    /**
     * @return the npj
     */
    public Npj getNpj() {
        return npj;
    }

    /**
     * @param npj the npj to set
     */
    public void setNpj(Npj npj) {
        this.npj = npj;
    }

    /**
     * @return the protocoloGsv
     */
    public ProtocoloGsv getProtocoloGsv() {
        return protocoloGsv;
    }

    /**
     * @param protocoloGsv the protocoloGsv to set
     */
    public void setProtocoloGsv(ProtocoloGsv protocoloGsv) {
        this.protocoloGsv = protocoloGsv;
    }

    /**
     * @return the calculo
     */
    public Calculo getCalculo() {
        return calculo;
    }

    /**
     * @param calculo the calculo to set
     */
    public void setCalculo(Calculo calculo) {
        this.calculo = calculo;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the expurgo
     */
    public Expurgo getExpurgo() {
        return expurgo;
    }

    /**
     * @param expurgo the expurgo to set
     */
    public void setExpurgo(Expurgo expurgo) {
        this.expurgo = expurgo;
    }

    /**
     * @return the metodologia
     */
    public Metodologia getMetodologia() {
        return metodologia;
    }

    /**
     * @param metodologia the metodologia to set
     */
    public void setMetodologia(Metodologia metodologia) {
        this.metodologia = metodologia;
    }

    /**
     * @return the funcionario
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    /**
     * @return the planoEconomico
     */
    public PlanoEconomico getPlanoEconomico() {
        return planoEconomico;
    }

    /**
     * @param planoEconomico the planoEconomico to set
     */
    public void setPlanoEconomico(PlanoEconomico planoEconomico) {
        this.planoEconomico = planoEconomico;
    }

    /**
     * @return the multa
     */
    public Multa getMulta() {
        return multa;
    }

    /**
     * @param multa the multa to set
     */
    public void setMulta(Multa multa) {
        this.multa = multa;
    }

    /**
     * @return the honorario
     */
    public Honorario getHonorario() {
        return honorario;
    }

    /**
     * @param honorario the honorario to set
     */
    public void setHonorario(Honorario honorario) {
        this.honorario = honorario;
    }

    /**
     * @return the mora
     */
    public Mora getMora() {
        return mora;
    }

    /**
     * @param mora the mora to set
     */
    public void setMora(Mora mora) {
        this.mora = mora;
    }

    /**
     * @return the arquivo
     */
    public Arquivo getArquivo() {
        return arquivo;
    }

    /**
     * @param arquivo the arquivo to set
     */
    public void setArquivo(Arquivo arquivo) {
        this.arquivo = arquivo;
    }

    /**
     * @return the atualizacao
     */
    public Atualizacao getAtualizacao() {
        return atualizacao;
    }

    /**
     * @param atualizacao the atualizacao to set
     */
    public void setAtualizacao(Atualizacao atualizacao) {
        this.atualizacao = atualizacao;
    }

    /**
     * @return the periodoCalculo
     */
    public PeriodoCalculo getPeriodoCalculo() {
        return periodoCalculo;
    }

    /**
     * @param periodoCalculo the periodoCalculo to set
     */
    public void setPeriodoCalculo(PeriodoCalculo periodoCalculo) {
        this.periodoCalculo = periodoCalculo;
    }

    /**
     * @return the protocoloGsvDAO
     */
    public ProtocoloGsvDAO<ProtocoloGsv, Object> getProtocoloGsvDAO() {
        return protocoloGsvDAO;
    }

    /**
     * @param protocoloGsvDAO the protocoloGsvDAO to set
     */
    public void setProtocoloGsvDAO(ProtocoloGsvDAO<ProtocoloGsv, Object> protocoloGsvDAO) {
        this.protocoloGsvDAO = protocoloGsvDAO;
    }

    /**
     * @return the expurgoDAO
     */
    public ExpurgoDAO<Expurgo, Object> getExpurgoDAO() {
        return expurgoDAO;
    }

    /**
     * @param expurgoDAO the expurgoDAO to set
     */
    public void setExpurgoDAO(ExpurgoDAO<Expurgo, Object> expurgoDAO) {
        this.expurgoDAO = expurgoDAO;
    }

}
