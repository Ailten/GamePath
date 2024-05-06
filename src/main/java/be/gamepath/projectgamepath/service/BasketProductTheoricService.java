package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.BasketProductTheoric;
import be.gamepath.projectgamepath.entities.ProductTheoricCategory;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class BasketProductTheoricService extends ServiceGeneric<BasketProductTheoric> {

    //no need to override selectById and selectMany for join table.

    /**
     * select one element by sending two foreign key.
     * @param em EntityManager.
     * @param idBasket is of first foreign key.
     * @param idProductTheoric is of first foreign key.
     * @return element find in DB (or null).
     */
    public BasketProductTheoric selectByBothId(EntityManager em, int idBasket, int idProductTheoric) {
        return em.createNamedQuery("BasketProductTheoric.SelectByBothId", BasketProductTheoric.class)
                .setParameter("idBasket", idBasket)
                .setParameter("idProductTheoric", idProductTheoric)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
