/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.controller;

import br.com.intranet.cenopservicoscwb.dao.ListaCompletaDAO;
import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
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
    private String estadoTela = "recolhido";
    private Part file;
    private ListaCompletaDAO<Calculo, Object> listaCompletaDAO;
   
    
    
    public ControleListaCompleta() {

        listaCompletaDAO = new ListaCompletaDAO<>();
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
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext externalContext = fc.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf\"");

            //FileInputStream inputStream = new FileInputStream(new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/"  + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            FileInputStream inputStream = new FileInputStream(new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
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
    
    
    public void downloadPcond(Calculo calculo) throws DocumentException, ParseException, FileNotFoundException, IOException {

        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext externalContext = fc.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + "PCOND" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf\"");

            //FileInputStream inputStream = new FileInputStream(new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/"  + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            FileInputStream inputStream = new FileInputStream(new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + "PCOND" + " - " + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
            //FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + calculo.getCliente().getNomeCliente() + " - " + Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf"));
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

   

    
}
