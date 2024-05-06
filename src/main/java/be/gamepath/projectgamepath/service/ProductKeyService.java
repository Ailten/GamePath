package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.entities.ProductKey;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class ProductKeyService extends ServiceGeneric<ProductKey> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public ProductKey selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("ProductKey.SelectById", ProductKey.class)
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
    public List<ProductKey> selectMany(EntityManager em)
    {
        return em.createNamedQuery("ProductKey.SelectMany", ProductKey.class)
                .getResultList();
    }

    /**
     * select many ProductKey, all assign to an Order.
     * @param em EntityManager.
     * @param idOrder id of Order.
     * @return list of ProductKey find in DB.
     */
    public List<ProductKey> selectManyByIdOrder(EntityManager em, int idOrder) {
        return em.createNamedQuery("ProductKey.SelectManyByIdOrder", ProductKey.class)
                .setParameter("idOrder", idOrder)
                .getResultList();
    }

    /**
     * select a ProductKey by sending a key.
     * @param em EntityManager.
     * @param key string of key (for unlock product).
     * @return a ProductKey (or null).
     */
    public ProductKey selectByKeyCode(EntityManager em, String key) {
        return em.createNamedQuery("ProductKey.SelectByKeyCode", ProductKey.class)
                .setParameter("key", key)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
