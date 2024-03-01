package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.entities.ProductTheoric;
import be.gamepath.projectgamepath.entities.ProductTheoricCategory;
import be.gamepath.projectgamepath.entities.RolePermission;
import be.gamepath.projectgamepath.utility.ServiceGeneric;
import be.gamepath.projectgamepath.utility.Utility;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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


    public List<ProductTheoric> selectManyByFilter(EntityManager em, String filter, boolean isShowEntityDisable){
        return em.createNamedQuery("ProductTheoric.SelectManyByFilter", ProductTheoric.class)
                .setParameter("filter", filter)
                .setParameter("isShowEntityDisable", isShowEntityDisable)
                .getResultList()
                //FIXME: remove filter date and apply it in query (using current_date() but seem not working).
                .stream().filter(pt -> isShowEntityDisable || LocalDateTime.now().isAfter(Utility.castDateToLocalDateTime(pt.getReleaseDate())))
                .collect(Collectors.toList());
    }
    public List<ProductTheoric> selectManyByFilter(EntityManager em, String filter){
        return this.selectManyByFilter(em, filter, false);
    }

}
