package com.example.gamepath.service;

import com.example.gamepath.entities.SocietyProducerEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class SocietyProducerService {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public SocietyProducerEntity selectById(EntityManager em, int id)
    {
        List<SocietyProducerEntity> listEntityGet = em.createNamedQuery("SocietyProducerEntity.SelectById", SocietyProducerEntity.class)
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
    public List<SocietyProducerEntity> selectMany(EntityManager em)
    {
        return em.createNamedQuery("SocietyProducerEntity.SelectMany", SocietyProducerEntity.class)
                .getResultList();
    }

    /**
     * insert an entity in db.
     * @param em entity manager.
     * @param entityToInsert entity to insert.
     * @return entity inserted.
     */
    public SocietyProducerEntity insert(EntityManager em, SocietyProducerEntity entityToInsert)
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
    public SocietyProducerEntity update(EntityManager em, SocietyProducerEntity entityToUpdate)
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
    public void delete(EntityManager em, SocietyProducerEntity entityToDelete){
        if(!em.contains(entityToDelete))
            entityToDelete = em.merge(entityToDelete);
        em.remove(entityToDelete);
        em.flush();
    }

}
