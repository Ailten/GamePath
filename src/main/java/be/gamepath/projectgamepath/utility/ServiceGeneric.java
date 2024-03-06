package be.gamepath.projectgamepath.utility;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class ServiceGeneric<TEntity extends EntityGenerique> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public TEntity selectById(EntityManager em, int id) throws Exception
    {
        throw new Exception("ServiceGeneric was not override");
    }

    /**
     * get list of entity.
     * @param em entity manager.
     * @return list entity match.
     */
    public List<TEntity> selectMany(EntityManager em) throws Exception
    {
        throw new Exception("ServiceGeneric was not override");
    }

    /**
     * insert an entity in db.
     * @param em entity manager.
     * @param entityToInsert entity to insert.
     * @return entity inserted.
     */
    public TEntity insert(EntityManager em, TEntity entityToInsert)
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
    public TEntity update(EntityManager em, TEntity entityToUpdate)
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
    public void delete(EntityManager em, TEntity entityToDelete){
        if(!em.contains(entityToDelete))
            entityToDelete = em.merge(entityToDelete);
        em.remove(entityToDelete);
        em.flush();
    }

}
