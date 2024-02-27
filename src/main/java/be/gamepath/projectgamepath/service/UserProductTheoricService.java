package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.UserProductTheoric;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import java.util.List;

public class UserProductTheoricService extends ServiceGeneric<UserProductTheoric> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public UserProductTheoric selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("UserProductTheoric.SelectById", UserProductTheoric.class)
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
    public List<UserProductTheoric> selectMany(EntityManager em)
    {
        return em.createNamedQuery("UserProductTheoric.SelectMany", UserProductTheoric.class)
                .getResultList();
    }


}
