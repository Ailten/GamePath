package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.BasketProductTheoric;
import be.gamepath.projectgamepath.entities.ProductTheoricCategory;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;

public class BasketProductTheoricService extends ServiceGeneric<BasketProductTheoric> {

    //no need to override selectById and selectMany for join table.


    public BasketProductTheoric selectByBothId(EntityManager em, int idBasket, int idProductTheoric) {
        return em.createNamedQuery("BasketProductTheoric.SelectByBothId", BasketProductTheoric.class)
                .setParameter("idBasket", idBasket)
                .setParameter("idProductTheoric", idProductTheoric)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
