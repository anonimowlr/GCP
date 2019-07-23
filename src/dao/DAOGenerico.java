/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import br.com.intranet.cenopservicoscwb.model.entidade.Npj;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import jpa.EntityManagerUtil;

/**
 *
 * @author PC_LENOVO
 */
public class DAOGenerico implements Serializable {

    protected EntityManager em;
    
    public DAOGenerico() {

        em = EntityManagerUtil.getEntityManager();
    }    

    public void salvar(Npj npj) {
        try {
            em.getTransaction().begin();
            em.persist(npj);
            em.getTransaction().commit();            
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
    public void atualizar(Npj npj) {
        try {
            em.getTransaction().begin();
            em.merge(npj);
            em.getTransaction().commit();            
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
    public void apagar (Npj npj) {
        try {
            em.getTransaction().begin();
            em.remove(npj);
            em.getTransaction().commit();            
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
    public List<Npj> buscar (){
        String jpql = "From Npj";
        return em.createQuery(jpql).getResultList();
    }

}
