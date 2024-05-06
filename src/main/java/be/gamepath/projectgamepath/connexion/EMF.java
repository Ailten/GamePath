package be.gamepath.projectgamepath.connexion;

import javax.persistence.*;

/**
 * Class to get a connection to the database
 *
 * @author Renaud DIANA
 */
public class EMF {

    private static EntityManagerFactory emfInstance =
            Persistence.createEntityManagerFactory("gamepath");

    public static EntityManager createEM() {
        return emfInstance.createEntityManager();
    }

    public static EntityTransaction getTransaction(EntityManager em) {
        return em.getTransaction();
    }

    public static void transactionCommit(EntityManager em, EntityTransaction transaction) {
        em.flush();
        transaction.commit();
    }
    public static void transactionRollback(EntityManager em, EntityTransaction transaction) {
        transaction.rollback();
    }

}
