package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.User;
import be.gamepath.projectgamepath.entities.UserProductTheoric;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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



    public UserProductTheoric selectByBothId(EntityManager em, int idUser, int idProductTheoric){
        return em.createNamedQuery("UserProductTheoric.SelectByBothId", UserProductTheoric.class)
                .setParameter("idUser", idUser)
                .setParameter("idProductTheoric", idProductTheoric)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }


    public List<UserProductTheoric> selectManyByFilter(EntityManager em, String filter, int idUser) {
        return em.createNamedQuery("UserProductTheoric.SelectManyByFilter", UserProductTheoric.class)
                .setParameter("filter", filter)
                .setParameter("idUser", idUser)
                .getResultList();
    }

}
