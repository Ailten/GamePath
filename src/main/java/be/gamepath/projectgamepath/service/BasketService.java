package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Basket;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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


    /**
     * get basket of user (if has one).
     * @param em entity manager.
     * @param idUser id of user ask.
     * @return basket or null.
     */
    public Basket selectByIdUser(EntityManager em, int idUser){
        return em.createNamedQuery("Basket.SelectByIdUser", Basket.class)
                .setParameter("idUser", idUser)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }


}
