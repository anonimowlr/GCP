/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.controller;

import br.com.intranet.cenopservicoscwb.dao.ExpurgoDAO;
import br.com.intranet.cenopservicoscwb.dao.ListaCompletaDAO;
import br.com.intranet.cenopservicoscwb.dao.MetodologiaDAO;
import br.com.intranet.cenopservicoscwb.dao.PlanoEconomicoDAO;
import br.com.intranet.cenopservicoscwb.model.calculo.MotorCalculoPoupanca;
import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
import br.com.intranet.cenopservicoscwb.model.entidade.Expurgo;
import br.com.intranet.cenopservicoscwb.model.entidade.Metodologia;
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
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author f5078775
 */
@ManagedBean
@ViewScoped
public class ControleListaCompleta implements Serializable {

    private BigDecimal saldoNaDataBase;
    private String estadoTela = "buscar";
    private Part file;
    private ListaCompletaDAO<Calculo, Object> listaCompletaDAO;
    private PlanoEconomicoDAO<PlanoEconomico, Object> planoEconomicoDAO;
    private Calculo calculo;
    private MetodologiaDAO<Metodologia, Object> metodologiaDAO;
    private ExpurgoDAO<Expurgo, Object> expurgoDAO;
    private ProtocoloGsv protocoloGsv;
   
    
    
    public ControleListaCompleta() {

        listaCompletaDAO = new ListaCompletaDAO<>();
        planoEconomicoDAO = new PlanoEconomicoDAO<>();
        metodologiaDAO = new MetodologiaDAO<>();
        expurgoDAO = new ExpurgoDAO<>();
        calculo = new Calculo();
        
    }
    
    
    
    public void editar(Calculo calculo){
        
        setCalculo(calculo);
        mudarParaEditar();
        
    }
    
    
     public void downloadPdfResumo(ProtocoloGsv protocoloGsv) throws DocumentException, ParseException, FileNotFoundException, IOException {

        try {
            FileInputStream inputStream;
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext externalContext = fc.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + "Resumo de Calculo" + " - " + protocoloGsv.getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + protocoloGsv.getCdPrc().toString() + ".pdf\"");

            
            if(Utils.getIpHost().equals("172.20.0.33")){
                
             inputStream = new FileInputStream(new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + "NPJ" + protocoloGsv.getNpj().getNrPrc().toString() + "/"  + "Resumo de Calculo" + " - " + protocoloGsv.getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + protocoloGsv.getCdPrc().toString()+ ".pdf"));
                
            }else{
              
                
             inputStream = new FileInputStream(new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + protocoloGsv.getNpj().getNrPrc().toString() + "\\"+ "Resumo de Calculo" + " - " + protocoloGsv.getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + protocoloGsv.getCdPrc().toString() +  ".pdf"));
                
            }
            
            



            //FileInputStream inputStream = new FileInputStream(new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + "NPJ" + protocoloGsv.getNpj().getNrPrc().toString() + "/" + "Resumo de Calculo" + " - " + protocoloGsv.getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + protocoloGsv.getCdPrc().toString() + ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + protocoloGsv.getNpj().getNrPrc().toString() + "\\"+ "Resumo de Calculo" + " - " + protocoloGsv.getNpj().getNrPrc().toString() + " - " + " Protocolo Gsv" + " " + protocoloGsv.getCdPrc().toString() +  ".pdf"));
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

    public void removeLinhaCalculo(Calculo calculo) {

        try {

            calculo.setFuncionario(null);
            calculo.setCliente(null);
            calculo.setExpurgo(null);
            calculo.setPlanoEconomico(null);

            if (getListaCompletaDAO().deletar(calculo)) {

               calculo.getProtocoloGsv().getListaCalculo().remove(calculo);

                Util.mensagemInformacao(getListaCompletaDAO().getMensagem());

            } else {
                Util.mensagemErro(getListaCompletaDAO().getMensagem());
            }

        } catch (Exception e) {
            Util.mensagemErro(Util.getMensagemErro(e));
        }

    }

    
    
    public void testeValidaCalculo(Integer  index){
        
        Calculo calculo = getListaCompletaDAO().localizar(index);
        
        Util.mensagemInformacao(calculo.getValorFinal().toString());
    }
    
    
    
    public void avaliarParaImprimir(Integer index) throws DocumentException, ParseException, IOException{
        
        Calculo calculo = getListaCompletaDAO().localizar(index);
        
        if(calculo.isPcond()){
            downloadPcond(calculo);
        } else{
            downloadPdf(calculo);
        }
        
    }
    
    

    public void downloadPdf(Calculo calculo) throws DocumentException, ParseException, FileNotFoundException, IOException {

        try {
            FileInputStream inputStream;
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext externalContext = fc.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf\"");

            
            if(Utils.getIpHost().equals("172.20.0.33")){
                
             inputStream = new FileInputStream(new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/"  + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
                
            }else{
              inputStream = new FileInputStream(new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
                
            }
            
            
            
            
            
            //FileInputStream inputStream = new FileInputStream(new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
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
    
    
    public void downloadPcond(Calculo calculo) throws DocumentException, ParseException, FileNotFoundException, IOException {

        try {
            FileInputStream inputStream;
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext externalContext = fc.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + "PCOND" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf\"");

            if(Utils.getIpHost().equals("172.20.0.33")){
                
             inputStream = new FileInputStream(new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/"  + "PCOND" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
                
            }else{
                
             inputStream = new FileInputStream(new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + "PCOND" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) +  ".pdf"));
            }
            
            
            
            //FileInputStream inputStream = new FileInputStream(new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + "PCOND" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + "PCOND" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarContaTexto(calculo.getNumeroConta()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) +  ".pdf"));
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
    
    
    
     public void avaliarParaSalvar() throws ParseException, IOException, DocumentException, Exception {
       

        
        
  
            GerarPdf gerarPdf = new GerarPdf();

       

            MotorCalculoPoupanca motorCalculoPoupanca = new MotorCalculoPoupanca();
            
            switch (calculo.getMetodologia().getId()){
                case 1:
                    motorCalculoPoupanca.calcular(calculo);
                break;
                
                case 2:
                    motorCalculoPoupanca.calcularPj(calculo);
                break;
                
                default:
                    Util.mensagemErro(calculo.getMetodologia().getNomeMetodologia() + "não é uma metodologia válida");
            }

            atualizarCalculo(getCalculo());
            gerarPdf.gerarDocumentoResumo(getCalculo().getProtocoloGsv());
            
            mudarParaBuscar();
        
        
       
        
        

    }
    
      public void atualizarCalculo(Calculo calculo) {

        if (getListaCompletaDAO().atualizar(calculo)) {

            Util.mensagemInformacao(getListaCompletaDAO().getMensagem());
        } else {
            Util.mensagemErro(getListaCompletaDAO().getMensagem());

        }

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
     * @return the listaCompletaDAO
     */
    public ListaCompletaDAO<Calculo, Object> getListaCompletaDAO() {
        return listaCompletaDAO;
    }

    /**
     * @param listaCompletaDAO the listaCompletaDAO to set
     */
    public void setListaCompletaDAO(ListaCompletaDAO<Calculo, Object> listaCompletaDAO) {
        this.listaCompletaDAO = listaCompletaDAO;
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

    private void mudarParaEditar() {
        setEstadoTela("editar");
    }
    private void mudarParaBuscar() {
        setEstadoTela("buscar");
    }

    public boolean isEditar(){
        return  "editar".equals(getEstadoTela());
    }
    public boolean isBuscar(){
        return  "buscar".equals(getEstadoTela());
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

    private ProtocoloGsv getProtocoloGsv() {
        return protocoloGsv;
    }

    /**
     * @param protocoloGsv the protocoloGsv to set
     */
    public void setProtocoloGsv(ProtocoloGsv protocoloGsv) {
        this.protocoloGsv = protocoloGsv;
    }
   

    
}
