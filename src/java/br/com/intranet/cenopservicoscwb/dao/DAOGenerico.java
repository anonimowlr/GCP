/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.util.Util;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import jpa.EntityManagerUtil;

/**
 *
 * @author PC_LENOVO
 */
public class DAOGenerico<T, D> implements Serializable {

    private List<T> listaObjetos;
    private List<T> listaTodos;
    protected Class classePersistente;
    protected Class chaveComposta;
    protected String mensagem;
    protected EntityManager em;
    protected String ordem = "id";
    protected String filtro = "";
    protected Integer maximoObjeto;
    protected Integer posicaoAtual = 0;
    protected Integer totalObjetos;

    public DAOGenerico() {

        em = EntityManagerUtil.getEntityManager();
    }

    public List<T> getListaObjetos() { // retorna consulta paginada

        String jpql = "From " + classePersistente.getSimpleName();
        String where = " ";
        filtro = filtro.replace("[';-]", ""); // retirendo carateres de injeção de sql

        if (filtro.length() > 0) {
            if (ordem.equals("id")) {
                try {
                    Integer.parseInt(filtro);
                    where += " WHERE  " + ordem + "= '" + filtro + "' ";
                } catch (Exception e) {
                    mensagem = Util.getMensagemErro(e);
                    Util.mensagemErro(mensagem);
                }
            } else {
                where += " WHERE upper(" + ordem + " ) like '%" + filtro.toUpperCase().trim() + "%'  ";
            }

        }

        jpql += where;
        jpql += " order by  " + ordem;
        totalObjetos = em.createQuery(jpql).getResultList().size();

        return em.createQuery(jpql).setFirstResult(posicaoAtual).setMaxResults(maximoObjeto).getResultList();

    }

    public void primeiraPagina() {
        posicaoAtual = 0;
    }

    public void ultimaPagina() {
        int resto = totalObjetos % maximoObjeto;

        if (resto > 0) {
            posicaoAtual = totalObjetos - resto;
        } else {
            posicaoAtual = totalObjetos - maximoObjeto;
        }

    }

    public void paginaAnterior() {
        posicaoAtual -= maximoObjeto;
        if (posicaoAtual < 0) {
            posicaoAtual = 0;
        }

    }

    public void proximaPagina() {
        if ((posicaoAtual + maximoObjeto) < totalObjetos) {
            posicaoAtual += maximoObjeto;
        }
    }

    public String mensagemNavegacao() {
        int ate = posicaoAtual + maximoObjeto;

        if (ate > totalObjetos) {
            ate = totalObjetos;
        }

        return "Listando  de " + (posicaoAtual + 1) + " até " + ate + " de " + (totalObjetos) + " registros";

    }

    public void rollback() {
        if (em.getTransaction().isActive() == false) {
            em.getTransaction().begin();
        }

        em.getTransaction().rollback();
    }

    public boolean salvar(T objeto) {
        try {
            em.getTransaction().begin();
            em.persist(objeto);
            em.getTransaction().commit();
            mensagem = "Salvo com sucesso";
            return true;
        } catch (Exception e) {
            rollback();
           
            mensagem ="Erro ao salvar - "  + Util.getMensagemErro(e);
            return false;
        }

    }

    public boolean atualizar(T objeto) {
        try {            
            em.getTransaction().begin();
            em.flush();
            em.merge(objeto);
            em.getTransaction().commit();
            mensagem = "Atualizado com sucesso";
            return true;

        } catch (Exception e) {
            rollback();
            mensagem = "Erro ao atualizar - " + Util.getMensagemErro(e);
            return false;
        }

    }

    public boolean deletar(T objeto) {

        try {
            em.getTransaction().begin();
            em.remove(objeto);
            em.getTransaction().commit();
            mensagem = "Excluído com sucesso";
            return true;

        } catch (Exception e) {
            rollback();
            mensagem = "Erro ao excluir - " + classePersistente.getSimpleName() + " - " + Util.getMensagemErro(e);
            return false;
        }

    }

    public T localizar(Integer id) {
        T objeto = (T) em.find(classePersistente, id);
        return objeto;
    }

    public T localizarPorChaveComposta(D id) { // a chave componta é uma instancia de uma classe embedeble
        T objeto = (T) em.find(classePersistente, id);
        return objeto;
    }

    public void setListaObjetos(List<T> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public List<T> getListaTodos() {

        String jpql = "From  " + classePersistente.getSimpleName() + "  order by " + ordem;

        return em.createQuery(jpql).getResultList();
    }

    public void setListaTodos(List<T> listaTodos) {
        this.listaTodos = listaTodos;
    }

    public Class getClassePersistente() {
        return classePersistente;
    }

    public void setClassePersistente(Class classePersistente) {
        this.classePersistente = classePersistente;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public Integer getMaximoObjeto() {
        return maximoObjeto;
    }

    public void setMaximoObjeto(Integer maximoObjeto) {
        this.maximoObjeto = maximoObjeto;
    }

    public Integer getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Integer posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Integer getTotalObjetos() {
        return totalObjetos;
    }

    public void setTotalObjetos(Integer totalObjetos) {
        this.totalObjetos = totalObjetos;
    }

}
