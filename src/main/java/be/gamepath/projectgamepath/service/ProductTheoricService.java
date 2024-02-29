package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.entities.ProductTheoric;
import be.gamepath.projectgamepath.entities.ProductTheoricCategory;
import be.gamepath.projectgamepath.entities.RolePermission;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductTheoricService extends ServiceGeneric<ProductTheoric> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public ProductTheoric selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("ProductTheoric.SelectById", ProductTheoric.class)
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
    public List<ProductTheoric> selectMany(EntityManager em)
    {
        return em.createNamedQuery("ProductTheoric.SelectMany", ProductTheoric.class)
                .getResultList();
    }

    /**
     * get one entity (find by champ unique).
     * @param em entity manager.
     * @param title champ unique.
     * @return entity find.
     */
    public ProductTheoric selectByTitle(EntityManager em, String title){
        return em.createNamedQuery("ProductTheoric.SelectByTitle", ProductTheoric.class)
                .setParameter("title", title)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
