package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.entities.PictureProduct;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class PictureProductService extends ServiceGeneric<PictureProduct> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public PictureProduct selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("PictureProduct.SelectById", PictureProduct.class)
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
    public List<PictureProduct> selectMany(EntityManager em)
    {
        return em.createNamedQuery("PictureProduct.SelectMany", PictureProduct.class)
                .getResultList();
    }

    /**
     * select many PictureProduct, all assign to a ProductTheoric.
     * @param em EntityManager.
     * @param idProductTheoric id of ProductTheoric.
     * @return list of PictureProduct find in DB.
     */
    public List<PictureProduct> selectManyByIdProductTheoric(EntityManager em, int idProductTheoric) {
        return em.createNamedQuery("PictureProduct.SelectManyByIdProduct", PictureProduct.class)
                .setParameter("idProductTheoric", idProductTheoric)
                .getResultList();
    }

}
