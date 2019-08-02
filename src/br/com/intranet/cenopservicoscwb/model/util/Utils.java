/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.model.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static String getDataAtual() {

        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
        return (formatarDate.format(data));

    }

    public static java.sql.Date getDataAtualFormatoMysql() throws Exception {

        String data = getDataAtual();
        java.sql.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    public static java.sql.Date getDataDesconciliacaoFormatoMysql(Date d) throws Exception {

        SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
        String data = (formatarDate.format(d));

        java.sql.Date date = null;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        return date;
    }

    public static String getDataHoraAtualMysql() {
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(d);
    }

    public static Double converteParaDouble(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",")) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        Double n = Double.parseDouble(numeroTratado);

        return n;
    }

    public static String tratarVariavel(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",")) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;
    }

    public static String limparPontos(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;
    }

    public static String converterToMoney(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            double marcador = i / 3;
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && (i == tamanhoValorRecebido - 3 || i == tamanhoValorRecebido - 2)) {
                num = ",";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }

            if (tamanhoValorRecebido > 4) {
                if (i == tamanhoValorRecebido - 7 || i == tamanhoValorRecebido - 10 || i == tamanhoValorRecebido - 13) {
                    numeroTratado = numeroTratado + ".";
                }

            }
        }

        return numeroTratado;
    }

    public static Double converterStringParaDouble(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return Double.parseDouble(numeroTratado);
    }

    public static BigDecimal converterStringParaBigDecimal(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        BigDecimal numeroConvertido = new BigDecimal(numeroTratado);

        return numeroConvertido;
    }

    public static String tratarVariavel(Double v) {
        String numeroTratado = "";

        String w = Double.toString(v);

        int tamanhoValorRecebido = w.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = w.subSequence(i, i + 1).toString();

            if (num.equals("E")) {
                num = "";
                numeroTratado = numeroTratado + num;
                i = tamanhoValorRecebido;
            }

            if (num.equals(".") || num.equals("E")) {
                num = "";
                numeroTratado = numeroTratado + num;

            }

            if (num.equals(",")) {
                num = "";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;
    }

    public static String tratarNpj(Double v) {
        Long numero = v.longValue();
        System.out.println(numero);

        return numero.toString();

    }

    public static String tratarContaDepositaria(Double v) {

        Long numero = v.longValue();
        System.out.println(numero);

        return numero.toString();
    }

    public static String converterParteInteiraDouble(Double v) {
        String numeroTratado = "";

        String w = Double.toString(v);

        int tamanhoValorRecebido = w.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = w.subSequence(i, i + 1).toString();

            if (num.equals("E")) {
                num = "";
                numeroTratado = numeroTratado + num;
                i = tamanhoValorRecebido;
            }

            if (num.equals(".") || num.equals("E")) {
                num = "";
                numeroTratado = numeroTratado + num;
                i = tamanhoValorRecebido;

            }

            if (num.equals(",")) {
                num = "";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;
    }

    public static java.sql.Date formataData(String data) throws Exception {
        if (data == null || data.equals("")) {
            return null;
        }
        java.sql.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    public static String getAnoAtual() {

        String dataAtualTexto = getDataAtual();
        String ano = (String) dataAtualTexto.subSequence(6, 10);

        return ano;
    }

    public static String getDiaAtual() {

        String dataAtualTexto = getDataAtual();
        String dia = (String) dataAtualTexto.subSequence(0, 2);

        return dia;
    }

    public static String getMesAtual() {

        String dataAtualTexto = getDataAtual();
        String mes = (String) dataAtualTexto.subSequence(4, 5);

        return mes;
    }

    public static String formatDataTexto(String data) { // recebe data em texto no formafo yyyy - MM - dd e o converte para texto no formato dd/mm/yyyy

        String ano = (String) data.subSequence(0, 4);
        String mes = (String) data.subSequence(5, 7);
        String dia = (String) data.subSequence(8, 10);

        data = dia + "/" + mes + "/" + ano;
        return data;

    }

    public static Date converterNumeroEmData(Long numeroTipoLong) {

        long time1 = numeroTipoLong;
        Date date = new Date(time1);

        return date;

    }

    public static Long converterStringEmLong(String numeroTexto) {
        Long numeroRetornado = Long.parseLong(numeroTexto);

        return numeroRetornado;

    }

    public static void copyToClipBord(String npj) {

        int tamanhoValorRecebido = npj.length();

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String text = npj.subSequence(4, tamanhoValorRecebido).toString();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, null);
    }

    public static Date getDataHoraAtualMysqlDate() {
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();

        return (d);
    }

    public static String tratarConta(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (i == tamanhoValorRecebido - 2) {
                num = num + "-";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }

            if (tamanhoValorRecebido > 4) {
                if (i == tamanhoValorRecebido - 5 || i == tamanhoValorRecebido - 8 || i == tamanhoValorRecebido - 11 || i == tamanhoValorRecebido - 14) {
                    numeroTratado = numeroTratado + ".";
                }

            }
        }

        return numeroTratado;
    }

    public static boolean getIntervaloCorrecao(BigDecimal correcaoEsperada, BigDecimal correcaoDigitata) {

        BigDecimal primeiroValor = (BigDecimal) correcaoEsperada.subtract(new BigDecimal(1));
        BigDecimal ultimoValor = (BigDecimal) correcaoEsperada.add(new BigDecimal(1));

        if (correcaoDigitata.compareTo(primeiroValor) >= 0 && correcaoDigitata.compareTo(ultimoValor) <= 0) {
            return true;
        } else {
            return false;
        }

    }

    public static void main(String[] args) throws Exception {
        diferencaDataMes2(new Date("01/25/2003"), new Date("01/01/2015"));
    }

    public static double diferencaDataMes(Date dataInicial, Date dataFinal) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date dataInicio = dataInicial;
        Date dataFim = dataFinal;

        /*
		 * valor de um mes em milisegundos, sendo que os valores sao:
		 * 30 dias no mes, 24 horas no dia, 60 minutos por hora, 60 segundos por minuto e 1000 milisegundos  
         */
        final double MES_EM_MILISEGUNDOS = 30.0 * 24.0 * 60.0 * 60.0 * 1000.0;
        //final double MES_EM_MILISEGUNDOS = 2592000000.0;

        double numeroDeMeses = (double) ((dataFim.getTime() - dataInicio.getTime()) / MES_EM_MILISEGUNDOS);

        System.out.println("numero de meses: " + numeroDeMeses);
        return numeroDeMeses;
    }

    public static int diferencaDataMes2(Date dataInicial, Date dataFinal) throws ParseException {
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.setTime(dataInicial);
        cal2.setTime(dataFinal);

        int numeroDeMeses = cal2.get(Calendar.MONTH) - cal.get(Calendar.MONTH);
        int numeroDeMAnos = (cal2.get(Calendar.YEAR) - cal.get(Calendar.YEAR)) * 12;

        System.out.println("numero de meses: " + (numeroDeMeses + numeroDeMAnos));
        return numeroDeMeses;
    }

    public static String converteData(Date date) {
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = dateFormat.format(date);
       return dataFormatada;
    }
}
