package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.ProductKey;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
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


}
