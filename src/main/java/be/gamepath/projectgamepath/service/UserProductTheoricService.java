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

    /**
     * select many elements by two foreign key.
     * @param em EntityManager.
     * @param idUser first foreign key.
     * @param idProductTheoric second foreign key.
     * @return an UserProductTheoric (or null).
     */
    public UserProductTheoric selectByBothId(EntityManager em, int idUser, int idProductTheoric){
        return em.createNamedQuery("UserProductTheoric.SelectByBothId", UserProductTheoric.class)
                .setParameter("idUser", idUser)
                .setParameter("idProductTheoric", idProductTheoric)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    /**
     * select many UserProductTheoric from DB, by filter.
     * @param em EntityManager.
     * @param filter string filter.
     * @param filterCategoryId Category filter.
     * @param idUser User filter.
     * @return a list of UserProductTheoric.
     */
    public List<UserProductTheoric> selectManyByFilter(
            EntityManager em,
            String filter,
            int filterCategoryId,
            int idUser
    ) {
        return em.createNamedQuery("UserProductTheoric.SelectManyByFilter", UserProductTheoric.class)
                .setParameter("filter", filter)
                .setParameter("filterCategoryId", filterCategoryId)
                .setParameter("idUser", idUser)
                .getResultList();
    }

}
