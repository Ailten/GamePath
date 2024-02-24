package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.CategoryEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class CategoryService {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public CategoryEntity selectById(EntityManager em, int id)
    {
        List<CategoryEntity> listEntityGet = em.createNamedQuery("CategoryEntity.SelectById", CategoryEntity.class)
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
    public List<CategoryEntity> selectMany(EntityManager em)
    {
        return em.createNamedQuery("CategoryEntity.SelectMany", CategoryEntity.class)
                .getResultList();
    }

    /**
     * insert an entity in db.
     * @param em entity manager.
     * @param entityToInsert entity to insert.
     * @return entity inserted.
     */
    public CategoryEntity insert(EntityManager em, CategoryEntity entityToInsert)
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
    public CategoryEntity update(EntityManager em, CategoryEntity entityToUpdate)
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
    public void delete(EntityManager em, CategoryEntity entityToDelete){
        if(!em.contains(entityToDelete))
            entityToDelete = em.merge(entityToDelete);
        em.remove(entityToDelete);
        em.flush();
    }

}
