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
import br.com.intranet.cenopservicoscwb.model.calculo.MotorCalculoPoupanca;
import br.com.intranet.cenopservicoscwb.model.entidade.Arquivo;
import br.com.intranet.cenopservicoscwb.model.entidade.Atualizacao;
import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
import br.com.intranet.cenopservicoscwb.model.entidade.Cliente;
import br.com.intranet.cenopservicoscwb.model.entidade.Expurgo;
import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
import br.com.intranet.cenopservicoscwb.model.entidade.Honorario;
import br.com.intranet.cenopservicoscwb.model.entidade.Indice;
import br.com.intranet.cenopservicoscwb.model.entidade.JuroRemuneratorio;
import br.com.intranet.cenopservicoscwb.model.entidade.Metodologia;
import br.com.intranet.cenopservicoscwb.model.entidade.Mora;
import br.com.intranet.cenopservicoscwb.model.entidade.Multa;
import br.com.intranet.cenopservicoscwb.model.entidade.Npj;
import br.com.intranet.cenopservicoscwb.model.entidade.PeriodoCalculo;
import br.com.intranet.cenopservicoscwb.model.entidade.PlanoEconomico;
import br.com.intranet.cenopservicoscwb.model.entidade.ProtocoloGsv;
import br.com.intranet.cenopservicoscwb.model.pdf.GerarPdf;
import br.com.intranet.cenopservicoscwb.model.util.Utils;
import br.com.intranet.cenopservicoscwb.util.Util;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author f5078775
 */
@ManagedBean
@ViewScoped
public class ControleCalculo implements Serializable {

    private BigDecimal saldoNaDataBase;
    private String estadoTela = "recolhido";
    private Part file;
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
    private JuroRemuneratorio juroRemuneratorio;
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
        arquivo = new Arquivo();
        npjDAO = new NpjDAO<>();
        cliente = new Cliente();
        mora = new Mora();
        juroRemuneratorio = new JuroRemuneratorio();
        honorario = new Honorario();
        multa = new Multa();
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

            if (npj != null) { // SE RETIRAR ESSA CONSTRUÇÃO, AO ATUALIZAR NPJ COM PROTOCOLO DIFERENTE EXCLUI OS ANTERIORES
                setNpj(npj);
            }

            if (protocoloGsv != null && protocoloGsv.getListaCalculo().size() >= 1) {
                setNpj(protocoloGsv.getNpj());
                setProtocoloGsv(protocoloGsv);
                getNpj().adicionarProtocolo(getProtocoloGsv());
                return;

            } else {
                
                if(protocoloGsv==null){
                    getNpj().adicionarProtocolo(getProtocoloGsv());
                    getProtocoloGsv().setNpj(getNpj());
                    
                }

                
                salvar();
                setCalculo(new Calculo());
                getCalculo().setCliente(getCliente());
                getCliente().adicionarCalculo(getCalculo());
                getCalculo().setMora(getMora());
                getCalculo().setMulta(getMulta());
                getCalculo().setHonorario(getHonorario());
                getCalculo().setJuroRemuneratorio(getJuroRemuneratorio());
                getCalculo().setArquivo(getArquivo());
                getProtocoloGsv().adicionarCalculo(getCalculo());
                getProtocoloGsv().setNpj(getNpj());
            }

            setPeriodoCalculo(new PeriodoCalculo());
            getCalculo().adicionarPeriodoCalculo(getPeriodoCalculo());
            mudarParaEditar();

        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));
        }

    }

    public void duplicar() throws ParseException, IOException, DocumentException {

        if (saldoNaDataBase != null) {
            setSaldoNaDataBase(null);

        }

        if (getProtocoloGsv().getCdPrc() == null || getNpj().getNrPrc() == null) {
            Util.mensagemErro("NPJ ou Protocolo GSV inválidos");
            return;

        }

        if (isRecolhido()) {
            novo();
            mudarParaEditar();
            return;
        }

        if (getProtocoloGsv().getListaCalculo().size() >= 1 && getProtocoloGsv().getListaCalculo().get(getProtocoloGsv().getListaCalculo().size() - 1).getValorFinal() == null) {

            avaliarParaSalvar(getProtocoloGsv().getListaCalculo().get(getProtocoloGsv().getListaCalculo().size() - 1));

        } else {
            setCalculo(new Calculo());

            Calculo calculoUltimaLinha = getProtocoloGsv().getListaCalculo().get(getProtocoloGsv().getListaCalculo().size() - 1);

            getCalculo().setCliente(new Cliente());
            getCalculo().setArquivo(calculoUltimaLinha.getArquivo());
            getCalculo().setDiaBase(calculoUltimaLinha.getDiaBase());
            getCalculo().setNomeBanco(calculoUltimaLinha.getNomeBanco());
            getCalculo().setNumeroAgencia(calculoUltimaLinha.getNumeroAgencia());
            getCalculo().setNumeroConta(calculoUltimaLinha.getNumeroConta());
            getCalculo().setPlanoEconomico(calculoUltimaLinha.getPlanoEconomico());
            getCalculo().setSaldoBase(calculoUltimaLinha.getSaldoBase());

            setMora(new Mora());
            setCliente(getCalculo().getCliente());
            setHonorario(new Honorario());
            setMulta(new Multa());
            setJuroRemuneratorio(new JuroRemuneratorio());
            //setArquivo(new Arquivo());

            //getCalculo().setCliente(getCliente());
            getCliente().adicionarCalculo(getCalculo());
            getCalculo().setMora(getMora());
            getCalculo().setHonorario(getHonorario());
            getCalculo().setMulta(getMulta());
            getCalculo().setJuroRemuneratorio(getJuroRemuneratorio());
            getProtocoloGsv().adicionarCalculo(getCalculo());

            setPeriodoCalculo(new PeriodoCalculo());
            getPeriodoCalculo().setDataInicioCalculo(calculoUltimaLinha.getListaPeriodoCalculo().get(0).getDataInicioCalculo());
            getPeriodoCalculo().setDataFinalCalculo(calculoUltimaLinha.getListaPeriodoCalculo().get(0).getDataFinalCalculo());

            getCalculo().setArquivo(getArquivo());
            getCalculo().adicionarPeriodoCalculo(getPeriodoCalculo());
        }

    }
    
    
    public void selecionarTodos(){
        
        for (Calculo c : getProtocoloGsv().getListaCalculo()) {
            c.setSelecionado(true);
            
        }
        
    }
    
    public void gerarPdfResumo() throws IOException, DocumentException, ParseException{
        GerarPdf gerarPdf = new GerarPdf();
        gerarPdf.gerarDocumentoResumo(getProtocoloGsv());
    }
    

    public void salvar() {

        if (getNpjDAO().atualizar(getNpj())) {
            Util.mensagemInformacao(getNpjDAO().getMensagem());

        } else {

            Util.mensagemErro(getNpjDAO().getMensagem());
        }

    }

    public void calcularParaConferencia(Calculo calculo) {

        setCalculo(calculo);

        MotorCalculoPoupanca motorCalculoPoupanca = new MotorCalculoPoupanca();
        motorCalculoPoupanca.calcularParaConferencia(calculo);
        setSaldoNaDataBase(getCalculo().getSaldoBase().add(getCalculo().getRemuneracaoBasica().add(getCalculo().getJurosCreditado())));

    }

    public void upload(Calculo calculo) {
        setCalculo(calculo);
        setArquivo(new Arquivo());
        getCalculo().setArquivo(getArquivo());

        try (InputStream is = getFile().getInputStream()) {

            String tipoArquivo = getFile().getContentType();

            getCalculo().getArquivo().setNomeArquivo(getFile().getName());
            getCalculo().getArquivo().setTipoArquivo(tipoArquivo);

            if (!tipoArquivo.equals("image/jpeg")) {
                Util.mensagemErro("Selecione um arquivo do tipo image/jpeg ");
                return;
            }

            this.getArquivo().setNomeArquivo(getFile().getSubmittedFileName());
            //this.tamanhoArquivo=file.getSize();

            if (!Utils.criarDiretorio(getCalculo())) {
                Util.mensagemErro("Não foi possivel criar diretório para a imagem!!");
                return;
            }

            Files.copy(is, new java.io.File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString() + "/" + calculo.getCliente().getCpf() + "/" + calculo.getNumeroConta(), getCalculo().getArquivo().getNomeArquivo()).toPath());
            //Files.copy(is, new java.io.File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString() + "/" + calculo.getCliente().getCpf() + "/" + calculo.getNumeroConta(), getCalculo().getArquivo().getNomeArquivo()).toPath());
            //Files.copy(is, new java.io.File("/home/jocimar/Área de Trabalho/TestePlanilha/" + getCalculo().getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + getCalculo().getProtocoloGsv().getCdPrc().toString() + "/" + getCalculo().getCliente().getCpf() + "/" + getCalculo().getNumeroConta(), getCalculo().getArquivo().getNomeArquivo()).toPath());
            //getComplementoPoupanca().setImagemExtrato(fileName);

            Util.mensagemInformacao(getArquivo().getNomeArquivo() + tipoArquivo + " Enviado com sucesso!!");

        } catch (Exception ex) {
            Util.mensagemErro(Util.getMensagemErro(ex));
        }

    }

    public void avaliarAlteracaoParametros() throws IOException {

        if (isEditar()) {

            FacesContext.getCurrentInstance().getExternalContext().redirect("calculo.jsf");
            Util.mensagemErro("Alteração de parametros");
            return;

        }

    }
    
    
    public void mostrarEstadoSelecao(Calculo calculo){
        
        Util.mensagemInformacao(Boolean.toString(calculo.isSelecionado()));
    }
    

    public void removeLinhaCalculo(Calculo calculo) {

        try {
            if (getCalculoDAO().deletar(calculo)) {
                Util.mensagemInformacao(getCalculoDAO().getMensagem());
                getProtocoloGsv().getListaCalculo().remove(calculo);
            } else {
                Util.mensagemErro(getCalculoDAO().getMensagem());
            }

        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));
        }

    }

    public void atribuirDataInicialPlano(Calculo calculo) throws Exception {

        if (calculo.getPlanoEconomico().getId().equals(1)) {
            calculo.getListaPeriodoCalculo().get(0).setDataInicioCalculo(Utils.getDataPlanoVerao(calculo.getDiaBase().toString()));
            try {
                
                
                if(calculo.getListaPeriodoCalculo().get(0).getDataFinalCalculo()!=null){
                    return;
                    
                }
                calculo.getListaPeriodoCalculo().get(getCalculo().getListaPeriodoCalculo().size() - 1).setDataFinalCalculo(Utils.getDataAtualFormatoMysql());
            } catch (Exception ex) {
                Util.mensagemInformacao(Util.getMensagemErro(ex));
            }
        }

    }

    public void removerProtocolo() {

        getProtocoloGsvDAO().deletar(getProtocoloGsv());

    }

    public void avaliarParaSalvar(Calculo calculo) throws ParseException, IOException, DocumentException {
        setCalculo(calculo);
        GerarPdf gerarPdf = new GerarPdf();

        if (!calculo.getProtocoloGsv().getNpj().equals(getNpjDAO().localizar(getNpj().getNrPrc()))) {
            Util.mensagemErro("O protocolo " + calculo.getProtocoloGsv().getCdPrc().toString() + " " + "já está associado a outro NPJ:  " + getProtocoloGsvDAO().localizar(getProtocoloGsv().getCdPrc()).getNpj().getNrPrc().toString());
            return;
        }

        if (calculo.getId() == null) {
            complementarDadosCalculo();
            MotorCalculoPoupanca motorCalculoPoupanca = new MotorCalculoPoupanca();
            motorCalculoPoupanca.calcular(calculo);
            salvarCalculo(calculo);
            gerarPdf.gerarDocumentoResumo(calculo.getProtocoloGsv());
            

        } else {

            complementarDadosCalculo();
            MotorCalculoPoupanca motorCalculoPoupanca = new MotorCalculoPoupanca();
            motorCalculoPoupanca.calcular(calculo);

            atualizarCalculo(calculo);
            gerarPdf.gerarDocumentoResumo(calculo.getProtocoloGsv());
        }

    }

    public void salvarCalculo(Calculo calculo) {

        if (getCalculoDAO().salvar(calculo)) {
            Util.mensagemInformacao(getCalculoDAO().getMensagem());

        } else {

            Util.mensagemErro(getCalculoDAO().getMensagem());

        }

    }

    public void atualizarCalculo(Calculo calculo) {

        if (getCalculoDAO().atualizar(calculo)) {

            Util.mensagemInformacao(getCalculoDAO().getMensagem());
        } else {
            Util.mensagemErro(getCalculoDAO().getMensagem());

        }

    }

    public void teste() {
        Util.mensagemInformacao("Desenvolver o método");
    }

    public void gerarPdf(Calculo calculo) throws DocumentException, ParseException {

        try {
            GerarPdf gerarPdf = new GerarPdf();
            gerarPdf.gerarDocumento(calculo);
        } catch (IOException ex) {

            Util.mensagemErro(Util.getMensagemErro(ex));
        }

    }

    public void imprimirTodos() throws DocumentException, ParseException, IOException {
        for (Calculo c : getProtocoloGsv().getListaCalculo()) {

            downloadMassificadoPdf(c);

        }
    }

    public void downloadMassificadoPdf(Calculo calculo) throws DocumentException, ParseException, FileNotFoundException, IOException {

        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext externalContext = fc.getExternalContext();

            externalContext.responseReset();

            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf\"");

            //FileInputStream inputStream = new FileInputStream(new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            OutputStream out = externalContext.getResponseOutputStream();
            byte[] buffer = new byte[1024];
            int lenght;

            while ((lenght = inputStream.read(buffer)) > 0) {
                out.write(buffer);
            }

            out.flush();
            fc.responseComplete();

        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));

        }

    }

    public void downloadPdf(Calculo calculo) throws DocumentException, ParseException, FileNotFoundException, IOException {

        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext externalContext = fc.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf\"");

            FileInputStream inputStream = new FileInputStream(new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/"  + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            OutputStream out = externalContext.getResponseOutputStream();
            byte[] buffer = new byte[1024];
            int lenght;

            while ((lenght = inputStream.read(buffer)) > 0) {
                out.write(buffer);
            }

            out.flush();
            fc.responseComplete();
        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));

        }

    }
    
    
     public void downloadPdfResumo() throws DocumentException, ParseException, FileNotFoundException, IOException {

        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext externalContext = fc.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + "Resumo de Calculo" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString()+ ".pdf\"");

             FileInputStream inputStream = new FileInputStream(new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + "NPJ" + getProtocoloGsv().getNpj().getNrPrc().toString() + "/"  + "Resumo de Calculo" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString()+ ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + "NPJ" + getProtocoloGsv().getNpj().getNrPrc().toString() + "/"  + "Resumo de Calculo" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString()+ ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            OutputStream out = externalContext.getResponseOutputStream();
            byte[] buffer = new byte[1024];
            int lenght;

            while ((lenght = inputStream.read(buffer)) > 0) {
                out.write(buffer);
            }

            out.flush();
            fc.responseComplete();
        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));

        }

    }

    
    

    public void complementarDadosCalculo() {

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

        Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");

        try {

            getCalculo().setDataRealizacaoCalculo(Calendar.getInstance().getTime());

            Cliente cliente = getClienteDAO().localizarCliente(getCalculo().getCliente().getCpf());
            if (cliente != null) {
                getCalculo().setCliente(cliente);
            }

            Funcionario funcionario = getFuncionarioDAO().localizarFuncionarioPorChave(usuario.getChaveFunci());

            if (usuario.getNomeGerente() == null) {
                funcionario.setNomeGerente("");

            } else {
                funcionario.setNomeGerente(usuario.getNomeGerente());
            }
            funcionario.setNomeFunci(usuario.getNomeFunci());
            funcionario.setNomeFuncao(usuario.getNomeFuncao());

            getCalculo().setFuncionario(funcionario);

        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));
        }

    }

    public void limparCampoProtocolo() {

        if (getProtocoloGsv().getCdPrc() != null) {
            getProtocoloGsv().setCdPrc(null);
            Util.mensagemErro("Informe o novo protocolo vinculado à este NPJ.");

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

    /**
     * @return the juroRemuneratorio
     */
    public JuroRemuneratorio getJuroRemuneratorio() {
        return juroRemuneratorio;
    }

    /**
     * @param juroRemuneratorio the juroRemuneratorio to set
     */
    public void setJuroRemuneratorio(JuroRemuneratorio juroRemuneratorio) {
        this.juroRemuneratorio = juroRemuneratorio;
    }

    /**
     * @return the file
     */
    public Part getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(Part file) {
        this.file = file;
    }

    /**
     * @return the estadoTela
     */
    public String getEstadoTela() {
        return estadoTela;
    }

    /**
     * @param estadoTela the estadoTela to set
     */
    public void setEstadoTela(String estadoTela) {
        this.estadoTela = estadoTela;
    }

    public void mudarParaEditar() {
        setEstadoTela("editar");
    }

    public boolean isEditar() {
        return "editar".equals(getEstadoTela());
    }

    public boolean isRecolhido() {
        return "recolhido".equals(getEstadoTela());
    }

    /**
     * @return the saldoNaDataBase
     */
    public BigDecimal getSaldoNaDataBase() {
        return saldoNaDataBase;
    }

    /**
     * @param saldoNaDataBase the saldoNaDataBase to set
     */
    public void setSaldoNaDataBase(BigDecimal saldoNaDataBase) {
        this.saldoNaDataBase = saldoNaDataBase;
    }

}
