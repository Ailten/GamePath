package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Order;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import java.util.List;

public class OrderService extends ServiceGeneric<Order> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public Order selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("Order.SelectById", Order.class)
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
    public List<Order> selectMany(EntityManager em)
    {
        return em.createNamedQuery("Order.SelectMany", Order.class)
                .getResultList();
    }


}
