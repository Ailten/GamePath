package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.PictureProductEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class PictureProductService {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public PictureProductEntity selectById(EntityManager em, int id)
    {
        List<PictureProductEntity> listEntityGet = em.createNamedQuery("PictureProductEntity.SelectById", PictureProductEntity.class)
                .setParameter("id", id)
                .getResultList();

        //return the only one result, or null if more or less than one result.
        return (listEntityGet.size() == 1? listEntityGet.get(0): null);
    }

    /**
     * get list of entity.
     * @param em entity manager.
     * @return list entity match.
     */
    public List<PictureProductEntity> selectMany(EntityManager em)
    {
        return em.createNamedQuery("PictureProductEntity.SelectMany", PictureProductEntity.class)
                .getResultList();
    }

    /**
     * insert an entity in db.
     * @param em entity manager.
     * @param entityToInsert entity to insert.
     * @return entity inserted.
     */
    public PictureProductEntity insert(EntityManager em, PictureProductEntity entityToInsert)
    {
        em.persist(entityToInsert);
        em.flush();
        return entityToInsert;
    }

    /**
     * update an entity in db.
     * @param entityToUpdate entity to update.
     * @param em entity manager.
     * @return entity updated.
     */
    public PictureProductEntity update(EntityManager em, PictureProductEntity entityToUpdate)
    {
        em.merge(entityToUpdate);
        em.flush();
        return entityToUpdate;
    }

    /**
     * delete entity from db.
     * @param em entity manager.
     * @param entityToDelete entity to delete.
     */
    public void delete(EntityManager em, PictureProductEntity entityToDelete){
        if(!em.contains(entityToDelete))
            entityToDelete = em.merge(entityToDelete);
        em.remove(entityToDelete);
        em.flush();
    }

}
