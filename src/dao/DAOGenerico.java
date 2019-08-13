/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import jpa.EntityManagerUtil;

/**
 *
 * @author PC_LENOVO
 */
public class DAOGenerico<T> implements Serializable {

    private EntityManager em;
    private Class<T> classePersistente;
    
    public DAOGenerico() {

        em = EntityManagerUtil.getEntityManager();
    }    

    public void salvar(T objeto) {
        try {
            getEm().getTransaction().begin();
            getEm().persist(objeto);
            getEm().getTransaction().commit();            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void atualizar(T objeto) {
        try {
           
            getEm().getTransaction().begin();
            getEm().merge(objeto);
            getEm().getTransaction().commit();            
        } catch (Exception e) {
            System.out.println(e);
        }
    }    
    
    public void apagar (T objeto) {
        try {
            getEm().getTransaction().begin();
            getEm().remove(objeto);
            getEm().getTransaction().commit();            
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
    public List<T> buscar (){
       
        String jpql = "From " + classePersistente;
        return getEm().createQuery(jpql).getResultList();
    }
    
    public T buscarObjeto (Long id){
        T objeto = (T) getEm().find(classePersistente, id);
        return objeto;
    }
    
    public T buscarObjeto (Integer id){
        T objeto = (T) getEm().find(classePersistente, id);
        return objeto;
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @param em the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * @return the classePersistente
     */
    public Class<T> getClassePersistente() {
        return classePersistente;
    }

    /**
     * @param classePersistente the classePersistente to set
     */
    public void setClassePersistente(Class<T> classePersistente) {
        this.classePersistente = classePersistente;
    }

}
