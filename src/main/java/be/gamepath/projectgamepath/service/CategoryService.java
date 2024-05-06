package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class CategoryService extends ServiceGeneric<Category> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public Category selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("Category.SelectById", Category.class)
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
    public List<Category> selectMany(EntityManager em)
    {
        return em.createNamedQuery("Category.SelectMany", Category.class)
                .getResultList();
    }

    /**
     * select many categories, all assign to a productTheoric.
     * @param em EntityManager.
     * @param idProductTheoric id of productTheoric.
     * @return list of catégorie find in DB.
     */
    public List<Category> selectManyByIdProductTheoric(EntityManager em, int idProductTheoric) {
        return em.createNamedQuery("Category.SelectManyByIdProduct", Category.class)
                .setParameter("idProductTheoric", idProductTheoric)
                .getResultList();
    }


}
