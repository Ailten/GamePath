package com.example.gamepath.service;

import com.example.gamepath.entities.ProductKeyEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductKeyService {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public ProductKeyEntity selectById(EntityManager em, int id)
    {
        List<ProductKeyEntity> listEntityGet = em.createNamedQuery("ProductKeyEntity.SelectById", ProductKeyEntity.class)
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
    public List<ProductKeyEntity> selectMany(EntityManager em)
    {
        return em.createNamedQuery("ProductKeyEntity.SelectMany", ProductKeyEntity.class)
                .getResultList();
    }

    /**
     * insert an entity in db.
     * @param em entity manager.
     * @param entityToInsert entity to insert.
     * @return entity inserted.
     */
    public ProductKeyEntity insert(EntityManager em, ProductKeyEntity entityToInsert)
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
    public ProductKeyEntity update(EntityManager em, ProductKeyEntity entityToUpdate)
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
    public void delete(EntityManager em, ProductKeyEntity entityToDelete){
        if(!em.contains(entityToDelete))
            entityToDelete = em.merge(entityToDelete);
        em.remove(entityToDelete);
        em.flush();
    }

}