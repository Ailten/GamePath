package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Basket;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import java.util.List;

public class BasketService extends ServiceGeneric<Basket> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public Basket selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("Basket.SelectById", Basket.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    /**
     * get list of entity.
     * @param em entity manager.
     * @return list entity match.
     */
    public List<Basket> selectMany(EntityManager em)
    {
        return em.createNamedQuery("Basket.SelectMany", Basket.class)
                .getResultList();
    }


}
