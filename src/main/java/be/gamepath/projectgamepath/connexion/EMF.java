package be.gamepath.projectgamepath.connexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Class to get a connection to the database
 *
 * @author Renaud DIANA
 */
public class EMF {

//    protected EntityManager em;

    private static EntityManagerFactory emfInstance =
            Persistence.createEntityManagerFactory("gamepath");

//    public EMF(){
//        this.em = EMF.getEM();
//    }

//    public void close(){
//        this.em.close();
//    }

//    public EntityTransaction getTransaction(){
//        return this.em.getTransaction();
//    }

//    public static EntityManagerFactory getEMF() {
//        return emfInstance;
//    }

    public static EntityManager getEM() {
        return emfInstance.createEntityManager();
    }

}
