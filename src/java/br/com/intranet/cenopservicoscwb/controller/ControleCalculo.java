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
import br.com.intranet.cenopservicoscwb.dao.PeriodoDAO;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    private FileInputStream inputStream;
    private BigDecimal saldoNaDataBase;
    private String estadoTela = "recolhido";
    private Part file;
    private CalculoDAO<Calculo, Object> calculoDAO;
    private ExpurgoDAO<Expurgo, Object> expurgoDAO;
    private ProtocoloGsvDAO<ProtocoloGsv, Object> protocoloGsvDAO;
    private NpjDAO<Npj, Object> npjDAO;
    private PlanoEconomicoDAO<PlanoEconomico, Object> planoEconomicoDAO;
    private MetodologiaDAO<Metodologia, Object> metodologiaDAO;
    private PeriodoDAO<PeriodoDAO, Object> periodoDAO;
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
    private List<Calculo> listaCalculoPcond;
    private Calculo calculoParaPcond;
    

    public ControleCalculo() {

        npj = new Npj();
        protocoloGsv = new ProtocoloGsv();
        arquivo = new Arquivo();
        npjDAO = new NpjDAO<>();
        periodoDAO = new PeriodoDAO<>();
        cliente = new Cliente();
        mora = new Mora();
        juroRemuneratorio = new JuroRemuneratorio();
        honorario = new Honorario();
        calculoParaPcond = new Calculo();
        multa = new Multa();
        calculoDAO = new CalculoDAO<>();
        expurgoDAO = new ExpurgoDAO<>();
        protocoloGsvDAO = new ProtocoloGsvDAO<>();
        planoEconomicoDAO = new PlanoEconomicoDAO<>();
        metodologiaDAO = new MetodologiaDAO<>();
        indiceDAO = new IndiceDAO<>();
        funcionarioDAO = new FuncionarioDAO<>();
        listaCalculoPcond = new ArrayList<>();
        clienteDAO = new ClienteDAO<>();
        protocoloGsv.setMulta(multa);
        protocoloGsv.setHonorario(honorario);   

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
                if (getProtocoloGsv().getMulta() != null) {

                    setMulta(getProtocoloGsv().getMulta());
                    getProtocoloGsv().setMulta(getMulta());
                    setHonorario(getProtocoloGsv().getHonorario());
                    getProtocoloGsv().setHonorario(getProtocoloGsv().getHonorario());
                } else{
                    getProtocoloGsv().setMulta(getMulta());
                    getProtocoloGsv().setHonorario(getHonorario());
                }

                return;

            } else {

                if (protocoloGsv == null) {
                    getNpj().adicionarProtocolo(getProtocoloGsv());
                    getProtocoloGsv().setNpj(getNpj());

                }
                if (protocoloGsv != null && protocoloGsv.getMulta() != null) {

                    setMulta(protocoloGsv.getMulta());
                    getProtocoloGsv().setMulta(getMulta());
                    setHonorario(protocoloGsv.getHonorario());
                    getProtocoloGsv().setHonorario(getHonorario());

                }

                salvar();
                setCalculo(new Calculo());
                getCalculo().setCliente(getCliente());
                // getCliente().adicionarCalculo(getCalculo());
                getCalculo().setMora(getMora());
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

    public void gerarResumoPcond() throws ParseException, IOException, DocumentException, Exception {

        getListaCalculoPcond().clear();

        for (Calculo calculo : getProtocoloGsv().getListaCalculo()) {

            if (calculo.isPcond() == true) {

                setCalculoParaPcond(new Calculo());

                adicionarValorAtributosCalcPcond(calculo);

            }

        }

        if (getListaCalculoPcond().isEmpty()) {
            Util.mensagemErro("Selecione pelo menos um cálculo para gerar o resumo de PCOND!!");
            return;

        }

        if (getListaCalculoPcond().isEmpty() == false) {

            GerarPdf gerarPdf = new GerarPdf();
            gerarPdf.gerarDocumentoResumoPcond(getProtocoloGsv(), getListaCalculoPcond());

            donwloadResumoPcond();

        }

    }

    public void donwloadResumoPcond() {

        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext externalContext = fc.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + "Resumo de Calculo Interno (PCOND)" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() + ".pdf\"");

            if (Utils.getIpHost().equals("172.20.0.33")|| Utils.getIpHost().equals("172.20.0.154")) {

                setInputStream(new FileInputStream(new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + "NPJ" + getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + "Resumo de Calculo Interno (PCOND)" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() + ".pdf")));

            } else if (Utils.getIpHost().equals("192.168.1.101")) {

                setInputStream(new FileInputStream(new File("C:\\Users\\PC_LENOVO\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + "Resumo de Calculo Interno (PCOND)" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() + ".pdf")));

            } else {
                setInputStream(new FileInputStream(new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + "Resumo de Calculo Interno (PCOND)" + " - " + protocoloGsv.getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() + ".pdf")));

            }

            //FileInputStream inputStream = new FileInputStream(new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + "NPJ" + getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + "Resumo de Calculo" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() + ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + getProtocoloGsv().getNpj().getNrPrc().toString() + "\\"+ "Resumo de Calculo" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() +  ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + "Resumo de Calculo" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() + ".pdf"));
            OutputStream out = externalContext.getResponseOutputStream();
            byte[] buffer = new byte[1024];
            int lenght;

            while ((lenght = getInputStream().read(buffer)) > 0) {
                out.write(buffer);
            }

            out.flush();
            fc.responseComplete();
        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));

        }

    }

    public void duplicar() throws ParseException, IOException, DocumentException, Exception {

        getCalculoDAO().getEm().clear();
        if (getSaldoNaDataBase() != null) {
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
            setJuroRemuneratorio(new JuroRemuneratorio());
            setArquivo(new Arquivo());

            getCalculo().setCliente(getCliente());
            //getCliente().adicionarCalculo(getCalculo());
            getCalculo().setMora(getMora());
            getCalculo().setJuroRemuneratorio(getJuroRemuneratorio());
            getProtocoloGsv().adicionarCalculo(getCalculo());

            setPeriodoCalculo(new PeriodoCalculo());
            getPeriodoCalculo().setDataInicioCalculo(calculoUltimaLinha.getListaPeriodoCalculo().get(0).getDataInicioCalculo());
            getPeriodoCalculo().setDataFinalCalculo(calculoUltimaLinha.getListaPeriodoCalculo().get(0).getDataFinalCalculo());

            getCalculo().setArquivo(getArquivo());
            getCalculo().adicionarPeriodoCalculo(getPeriodoCalculo());
        }

    }

    public void selecionarTodos() {

        for (Calculo c : getProtocoloGsv().getListaCalculo()) {
            c.setPcond(true);

        }

    }

    public void gerarPdfResumo() throws IOException, DocumentException, ParseException {
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

    public void configuraLinha(Calculo calculo) {
        if (calculo.getMetodologia().getId() == 2) {
            calculo.getListaPeriodoCalculo().get(0).setDataInicioCalculo(null);
        }

         if (calculo.getMetodologia().getId() == 3) {
            Indice indice = getCalculoDAO().getEm().find(Indice.class, 3);
            calculo.getListaPeriodoCalculo().get(0).setIndice(indice);
        }
    }

    public void calcularParaConferencia(Calculo calculo) {

        if (calculo.getMetodologia().getId() == 2 && calculo.getListaPeriodoCalculo().get(0).getDataInicioCalculo() == null) {
            return;
        }

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

            if (Utils.getIpHost().equals("172.20.0.33")|| Utils.getIpHost().equals("172.20.0.154")) {

                Files.copy(is, new java.io.File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString() + "/" + calculo.getCliente().getCpf() + "/" + calculo.getNumeroConta(), getCalculo().getArquivo().getNomeArquivo()).toPath());
            } else if (Utils.getIpHost().equals("192.168.1.101")) {

                Files.copy(is, new java.io.File("C:\\Users\\PC_LENOVO\\Desktop\\DistribuidorPoupancaTeste\\" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString() + "/" + calculo.getCliente().getCpf() + "/" + calculo.getNumeroConta(), getCalculo().getArquivo().getNomeArquivo()).toPath());
            } else {
                Files.copy(is, new java.io.File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString() + "/" + calculo.getCliente().getCpf() + "/" + calculo.getNumeroConta(), getCalculo().getArquivo().getNomeArquivo()).toPath());

            }

            //Files.copy(is, new java.io.File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString() + "/" + calculo.getCliente().getCpf() + "/" + calculo.getNumeroConta(), getCalculo().getArquivo().getNomeArquivo()).toPath());
            //Files.copy(is, new java.io.File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString() + "/" + calculo.getCliente().getCpf() + "/" + calculo.getNumeroConta(), getCalculo().getArquivo().getNomeArquivo()).toPath());
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

    public void mostrarEstadoSelecao(Calculo calculo) {

        Util.mensagemInformacao(Boolean.toString(calculo.isPcond()));
    }

    public void consultaCalculoCpf(String cpf) {
        List<Calculo> listaCalculo = new ArrayList<>();
        listaCalculo = getCalculoDAO().consultaCalculoCpf(cpf);

        if (listaCalculo.size() > 0) {
            Util.mensagemAlerta("Há cálculos vinculados a este CPF.");
        }

    }

    public void removeLinhaCalculo(Calculo calculo) {

        try {

            calculo.setFuncionario(null);
            calculo.setCliente(null);
            calculo.setExpurgo(null);
            calculo.setPlanoEconomico(null);

            if (getCalculoDAO().deletar(calculo)) {

                getProtocoloGsv().getListaCalculo().remove(calculo);
                getProtocoloGsvDAO().salvar(getProtocoloGsv());

                gerarPdfResumo();
                
                Util.mensagemInformacao(getCalculoDAO().getMensagem());

            } else {
                Util.mensagemErro(getCalculoDAO().getMensagem());
            }

        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));
        }

    }

    public void atribuirDataInicialPlano(Calculo calculo) throws Exception {

        if ((calculo.getPlanoEconomico().getId() == 1 || calculo.getPlanoEconomico().getId() == 2) && calculo.getDiaBase() > 15) {
            Util.mensagemErro("Nos Planos BRESSER e VERÃO o dia base não pode ser superior a 15");
            return;
        }

        switch (calculo.getMetodologia().getId()) {
            case 1:
                if (calculo.getPlanoEconomico().getId().equals(1)) {
                    calculo.getListaPeriodoCalculo().get(0).setDataInicioCalculo(Utils.getDataPlanoVerao(calculo.getDiaBase().toString()));
                    try {

                        if (calculo.getListaPeriodoCalculo().get(0).getDataFinalCalculo() != null) {
                            return;

                        }
                        calculo.getListaPeriodoCalculo().get(getCalculo().getListaPeriodoCalculo().size() - 1).setDataFinalCalculo(Utils.getDataAtualFormatoMysql());
                    } catch (Exception ex) {
                        Util.mensagemInformacao(Util.getMensagemErro(ex));
                    }
                } else if (calculo.getPlanoEconomico().getId().equals(2)) {
                    calculo.getListaPeriodoCalculo().get(0).setDataInicioCalculo(Utils.getDataPlanoBresser(calculo.getDiaBase().toString()));
                    try {

                        if (calculo.getListaPeriodoCalculo().get(0).getDataFinalCalculo() != null) {
                            return;

                        }
                        calculo.getListaPeriodoCalculo().get(getCalculo().getListaPeriodoCalculo().size() - 1).setDataFinalCalculo(Utils.getDataAtualFormatoMysql());
                    } catch (Exception ex) {
                        Util.mensagemInformacao(Util.getMensagemErro(ex));
                    }

                }
                break;

            case 3:
                if (calculo.getPlanoEconomico().getId().equals(1)) {
                    calculo.getListaPeriodoCalculo().get(0).setDataInicioCalculo(Utils.getDataPlanoVerao(calculo.getDiaBase().toString()));
                    try {

                        if (calculo.getListaPeriodoCalculo().get(0).getDataFinalCalculo() != null) {
                            return;

                        }
                        calculo.getListaPeriodoCalculo().get(getCalculo().getListaPeriodoCalculo().size() - 1).setDataFinalCalculo(Utils.getDataAtualFormatoMysql());
                    } catch (Exception ex) {
                        Util.mensagemInformacao(Util.getMensagemErro(ex));
                    }
                } else if (calculo.getPlanoEconomico().getId().equals(2)) {
                    calculo.getListaPeriodoCalculo().get(0).setDataInicioCalculo(Utils.getDataPlanoBresser(calculo.getDiaBase().toString()));
                    try {

                        if (calculo.getListaPeriodoCalculo().get(0).getDataFinalCalculo() != null) {
                            return;

                        }
                        calculo.getListaPeriodoCalculo().get(getCalculo().getListaPeriodoCalculo().size() - 1).setDataFinalCalculo(Utils.getDataAtualFormatoMysql());
                    } catch (Exception ex) {
                        Util.mensagemInformacao(Util.getMensagemErro(ex));
                    }

                }
                break;

            case 4:
                if (calculo.getPlanoEconomico().getId().equals(1)) {
                    calculo.getListaPeriodoCalculo().get(0).setDataInicioCalculo(Utils.getDataPlanoVerao(calculo.getDiaBase().toString()));
                    try {

                        if (calculo.getListaPeriodoCalculo().get(0).getDataFinalCalculo() != null) {
                            return;

                        }
                        calculo.getListaPeriodoCalculo().get(getCalculo().getListaPeriodoCalculo().size() - 1).setDataFinalCalculo(Utils.getDataAtualFormatoMysql());
                    } catch (Exception ex) {
                        Util.mensagemInformacao(Util.getMensagemErro(ex));
                    }
                }
                break;

        }

    }

    public void removerProtocolo() {

        getProtocoloGsvDAO().deletar(getProtocoloGsv());

    }

    public void alterarClienteCalculo(Calculo calculo) throws ParseException, IOException, DocumentException {

        try {

            if (calculo.getId() != null) {
                Cliente cliente = getClienteDAO().localizarCliente(calculo.getCliente().getCpf());
                if (cliente != null) {
                    calculo.setCliente(cliente);

                } else {

                    setCliente(new Cliente());
                    getCliente().setNomeCliente(calculo.getCliente().getNomeCliente());
                    getCliente().setCpf(calculo.getCliente().getCpf());
                    calculo.setCliente(getCliente());

                }

            }

            consultaCalculoCpf(calculo.getCliente().getCpf());

        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));
        }

    }

    public void avaliarParaSalvar(Calculo calculo) throws ParseException, IOException, DocumentException, Exception {
        setCalculo(calculo);

        if (calculo.getMetodologia().getId() == 2) {
            calculo.setDiaBase(Utils.getDia(calculo.getListaPeriodoCalculo().get(0).getDataInicioCalculo()));
        }

        if (calculo.getMora().getDataInicio().after(calculo.getListaPeriodoCalculo().get(calculo.getListaPeriodoCalculo().size() - 1).getDataFinalCalculo())) {
            Util.mensagemErro("Data de mora não pode ser superior à data de atualização do cálculo.");
            return;
        }

        GerarPdf gerarPdf = new GerarPdf();

        if (!calculo.getProtocoloGsv().getNpj().equals(getNpjDAO().localizar(getNpj().getNrPrc()))) {
            Util.mensagemErro("O protocolo " + calculo.getProtocoloGsv().getCdPrc().toString() + " " + "já está associado a outro NPJ:  " + getProtocoloGsvDAO().localizar(getProtocoloGsv().getCdPrc()).getNpj().getNrPrc().toString());
            return;
        }

        if (calculo.getId() == null) {
            complementarDadosCalculo(calculo);
            MotorCalculoPoupanca motorCalculoPoupanca = new MotorCalculoPoupanca();

            switch (calculo.getMetodologia().getId()) {
                case 1:                    
                    motorCalculoPoupanca.calcular(calculo);
                    break;
                case 2:
                    motorCalculoPoupanca.calcularPj(calculo);
                    break;
                case 3:
                    motorCalculoPoupanca.calcularDiferencaApadecoCumprimentoSentenca(calculo);
                    break;
                case 4:
                    motorCalculoPoupanca.calcular(calculo);
                    break;
                default:
                    Util.mensagemErro(calculo.getMetodologia().getNomeMetodologia() + "não é uma metodologia válida");
                    return;
            }

            if (calculo.isPcond() == false) {
                salvarCalculo(calculo);
                gerarPdf.gerarDocumentoResumo(calculo.getProtocoloGsv());
            }

        } else {
            complementarDadosCalculo(calculo);
            MotorCalculoPoupanca motorCalculoPoupanca = new MotorCalculoPoupanca();

            switch (calculo.getMetodologia().getId()) {
                case 1:
                    motorCalculoPoupanca.calcular(calculo);
                    break;
                case 2:
                    motorCalculoPoupanca.calcularPj(calculo);
                    break;
                case 3:
                    motorCalculoPoupanca.calcularDiferencaApadecoCumprimentoSentenca(calculo);
                    break;
                case 4:
                    motorCalculoPoupanca.calcular(calculo);
                    break;
                default:
                    Util.mensagemErro(calculo.getMetodologia().getNomeMetodologia() + "não é uma metodologia válida");
                    return;
            }

            if (calculo.isPcond() == false) {
                atualizarCalculo(calculo);
                gerarPdf.gerarDocumentoResumo(calculo.getProtocoloGsv());
            }
        }
    }

    public void alterarParametrosParaPcond(Calculo calculoParaPcond) throws ParseException, IOException, DocumentException {

        if (!calculoParaPcond.isPcond()) {
            return;
        }

        calculoParaPcond.getListaPeriodoCalculo().get(0).setDataFinalCalculo(Utils.getDataHoraAtualMysqlDate());
        calculoParaPcond.getListaPeriodoCalculo().get(0).setIndice(getIndiceDAO().localizar(1));
        calculoParaPcond.setMora(calculoParaPcond.getMora());
        Honorario honorarioPcond = new Honorario();
        calculoParaPcond.setHonorario(honorarioPcond);
        Multa multaPcond = new Multa();
        calculoParaPcond.setMulta(multaPcond);
        calculoParaPcond.setPlanoEconomico(getPlanoEconomicoDAO().localizar(1));

        Mora mora = new Mora();

        if (calculoParaPcond.getNomeBanco().equals("BB")) {
            mora.setDataInicio(new Date("06/08/1993"));

        } else if (calculoParaPcond.getNomeBanco().equals("BNC")) {
            mora.setDataInicio(new Date("06/21/1993"));

        } else {
            mora.setDataInicio(new Date("05/19/1993"));
        }

        calculoParaPcond.setMora(mora);

        calculoParaPcond.setExpurgo(getExpurgoDAO().localizar(2));
        calculoParaPcond.setJuroRemuneratorio(new JuroRemuneratorio());

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

    public void teste(Calculo calculo) {
        Util.mensagemInformacao("Desenvolver o método...." + calculo.getCliente().getNomeCliente());
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
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf\"");

            //FileInputStream inputStream = new FileInputStream(new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
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

    public void adicionarValorAtributosCalcPcond(Calculo calculo) throws Exception {
        getCalculoParaPcond().setSaldoBase(calculo.getSaldoBase());
        getCalculoParaPcond().setCliente(calculo.getCliente());
        getCalculoParaPcond().setNomeBanco(calculo.getNomeBanco());
        getCalculoParaPcond().setNumeroAgencia(calculo.getNumeroAgencia());
        getCalculoParaPcond().setNumeroConta(calculo.getNumeroConta());
        getCalculoParaPcond().setDiaBase(calculo.getDiaBase());
        getCalculoParaPcond().setListaPeriodoCalculo(calculo.getListaPeriodoCalculo());
        getCalculoParaPcond().setMora(calculo.getMora());
        getCalculoParaPcond().setMetodologia(calculo.getMetodologia());
        getCalculoParaPcond().setHonorario(calculo.getHonorario());
        getCalculoParaPcond().setMulta(calculo.getMulta());
        getCalculoParaPcond().setProtocoloGsv(calculo.getProtocoloGsv());
        getCalculoParaPcond().setFuncionario(calculo.getFuncionario());

        getCalculoParaPcond().setPcond(true);
        alterarParametrosParaPcond(getCalculoParaPcond());
        MotorCalculoPoupanca motorCalculoPoupanca = new MotorCalculoPoupanca();
        motorCalculoPoupanca.calcularPcondparaListaResumo(getCalculoParaPcond());
        getListaCalculoPcond().add(getCalculoParaPcond());
    }

    public void avaliarParaImprimir(Calculo calculo) throws DocumentException, ParseException, IOException, Exception {
        if (calculo.isPcond()) {

            getCalculoParaPcond().setSaldoBase(calculo.getSaldoBase());
            getCalculoParaPcond().setCliente(calculo.getCliente());
            getCalculoParaPcond().setNomeBanco(calculo.getNomeBanco());
            getCalculoParaPcond().setNumeroAgencia(calculo.getNumeroAgencia());
            getCalculoParaPcond().setNumeroConta(calculo.getNumeroConta());
            getCalculoParaPcond().setDiaBase(calculo.getDiaBase());
            getCalculoParaPcond().setListaPeriodoCalculo(calculo.getListaPeriodoCalculo());
            getCalculoParaPcond().setMora(calculo.getMora());
            getCalculoParaPcond().setMetodologia(calculo.getMetodologia());
            getCalculoParaPcond().setHonorario(calculo.getHonorario());
            getCalculoParaPcond().setMulta(calculo.getMulta());
            getCalculoParaPcond().setProtocoloGsv(calculo.getProtocoloGsv());
            getCalculoParaPcond().setFuncionario(calculo.getFuncionario());

            getCalculoParaPcond().setPcond(true);

            gerarDocumentoPcondSemSalvar(getCalculoParaPcond());
            downloadPcond(getCalculoParaPcond());
        } else {
            downloadPdf(calculo);
        }
    }

    public void downloadPdf(Calculo calculo) throws DocumentException, ParseException, FileNotFoundException, IOException {

        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext externalContext = fc.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + "CALCULO DEFESA" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf\"");

            if (Utils.getIpHost().equals("172.20.0.33") || Utils.getIpHost().equals("172.20.0.154")) {

                setInputStream(new FileInputStream(new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + "CALCULO DEFESA" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf")));

            } else if (Utils.getIpHost().equals("192.168.1.101")) {
                setInputStream(new FileInputStream(new File("C:\\Users\\PC_LENOVO\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + "CALCULO DEFESA" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf")));

            } else {
                setInputStream(new FileInputStream(new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + "CALCULO DEFESA" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf")));

            }

            //FileInputStream inputStream = new FileInputStream(new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            OutputStream out = externalContext.getResponseOutputStream();

            byte[] buffer = new byte[1024];
            int lenght;

            while ((lenght = getInputStream().read(buffer)) > 0) {
                out.write(buffer);
            }

            out.flush();
            fc.responseComplete();
        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));

        }

    }
    
    
    
    
    

    public void downloadPcond(Calculo calculo) throws DocumentException, ParseException, FileNotFoundException, IOException {

        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext externalContext = fc.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + "CALCULO INTERNO (PCOND)" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf\"");

            if (Utils.getIpHost().equals("172.20.0.33")|| Utils.getIpHost().equals("172.20.0.154")) {

                setInputStream(new FileInputStream(new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + "CALCULO INTERNO (PCOND)" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf")));

            } else if (Utils.getIpHost().equals("192.168.1.101")) {

                setInputStream(new FileInputStream(new File("C:\\Users\\PC_LENOVO\\Desktop\\DistribuidorPoupancaTeste\\" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + "CALCULO INTERNO (PCOND)" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf")));

            } else {
                setInputStream(new FileInputStream(new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + "CALCULO INTERNO (PCOND)" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf")));

            }

            //FileInputStream inputStream = new FileInputStream(new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + "PCOND" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + "PCOND" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            OutputStream out = externalContext.getResponseOutputStream();
            byte[] buffer = new byte[1024];
            int lenght;

            while ((lenght = getInputStream().read(buffer)) > 0) {
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
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + "Resumo de Calculo" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() + ".pdf\"");

            if (Utils.getIpHost().equals("172.20.0.33")|| Utils.getIpHost().equals("172.20.0.154")) {

                setInputStream(new FileInputStream(new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + "NPJ" + getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + "Resumo de Calculo" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() + ".pdf")));

            } else if (Utils.getIpHost().equals("192.168.1.101")) {

                setInputStream(new FileInputStream(new File("C:\\Users\\PC_LENOVO\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + getCalculo().getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + "Resumo de Calculo" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() + ".pdf")));

            } else {
                setInputStream(new FileInputStream(new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + getCalculo().getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + "Resumo de Calculo" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() + ".pdf")));

            }

            //FileInputStream inputStream = new FileInputStream(new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + "NPJ" + getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + "Resumo de Calculo" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() + ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + getProtocoloGsv().getNpj().getNrPrc().toString() + "\\"+ "Resumo de Calculo" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() +  ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + "Resumo de Calculo" + " - " + getProtocoloGsv().getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + getProtocoloGsv().getCdPrc().toString() + ".pdf"));
            OutputStream out = externalContext.getResponseOutputStream();
            byte[] buffer = new byte[1024];
            int lenght;

            while ((lenght = getInputStream().read(buffer)) > 0) {
                out.write(buffer);
            }

            out.flush();
            fc.responseComplete();
        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));

        }

    }

    public void complementarDadosCalculo(Calculo calculo) {

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

        Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");

        try {

            if (calculo.getMetodologia().getId() == 3) {
                calculo.getMora().setDataInicio(new Date("07/01/1994"));
                calculo.setPcond(false);
                Indice indice = getIndiceDAO().getEm().find(Indice.class, 3);
                calculo.getListaPeriodoCalculo().get(0).setIndice(indice);
            }

            if (calculo.getMetodologia().getId() == 4) {
                calculo.getJuroRemuneratorio().setDataInicio(calculo.getListaPeriodoCalculo().get(0).getDataInicioCalculo());
                calculo.getJuroRemuneratorio().setDataFinal(calculo.getListaPeriodoCalculo().get(0).getDataFinalCalculo());
                Expurgo expurgoParaApadecoJuroRem = getExpurgoDAO().getEm().find(Expurgo.class, 2);
                calculo.setExpurgo(expurgoParaApadecoJuroRem);
                calculo.setPcond(false);
            }

            calculo.setDataRealizacaoCalculo(Calendar.getInstance().getTime());
            calculo.getCliente().setNomeCliente(calculo.getCliente().getNomeCliente().toUpperCase());

            Cliente cliente = getClienteDAO().localizarCliente(calculo.getCliente().getCpf());
            if (cliente != null) {
                calculo.setCliente(cliente);
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

    /**
     * @return the periodoDAO
     */
    public PeriodoDAO<PeriodoDAO, Object> getPeriodoDAO() {
        return periodoDAO;
    }

    /**
     * @param periodoDAO the periodoDAO to set
     */
    public void setPeriodoDAO(PeriodoDAO<PeriodoDAO, Object> periodoDAO) {
        this.periodoDAO = periodoDAO;
    }

    private void gerarDocumentoPcondSemSalvar(Calculo calculoParaPcond) throws ParseException, IOException, DocumentException, Exception {

        alterarParametrosParaPcond(calculoParaPcond);
        MotorCalculoPoupanca motorCalculo = new MotorCalculoPoupanca();
        motorCalculo.calcular(calculoParaPcond);

    }

    /**
     * @return the inputStream
     */
    public FileInputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param inputStream the inputStream to set
     */
    public void setInputStream(FileInputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * @return the listaCalculoPcond
     */
    public List<Calculo> getListaCalculoPcond() {
        return listaCalculoPcond;
    }

    /**
     * @param listaCalculoPcond the listaCalculoPcond to set
     */
    public void setListaCalculoPcond(List<Calculo> listaCalculoPcond) {
        this.listaCalculoPcond = listaCalculoPcond;
    }

    /**
     * @return the calculoParaPcond
     */
    public Calculo getCalculoParaPcond() {
        return calculoParaPcond;
    }

    /**
     * @param calculoParaPcond the calculoParaPcond to set
     */
    public void setCalculoParaPcond(Calculo calculoParaPcond) {
        this.calculoParaPcond = calculoParaPcond;
    }

}
