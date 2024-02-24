package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.RolePermissionEntity;
import be.gamepath.projectgamepath.entities.UserEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class UserService {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public UserEntity selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("UserEntity.SelectById", UserEntity.class)
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
    public List<UserEntity> selectMany(EntityManager em)
    {
        return em.createNamedQuery("UserEntity.SelectMany", UserEntity.class)
                .getResultList();
    }

    /**
     * insert an entity in db.
     * @param em entity manager.
     * @param entityToInsert entity to insert.
     * @return entity inserted.
     */
    public UserEntity insert(EntityManager em, UserEntity entityToInsert)
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
    public UserEntity update(EntityManager em, UserEntity entityToUpdate)
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
    public void delete(EntityManager em, UserEntity entityToDelete){
        if(!em.contains(entityToDelete))
            entityToDelete = em.merge(entityToDelete);
        em.remove(entityToDelete);
        em.flush();
    }

    public List<RolePermissionEntity> selectRolePermissionOfUser(EntityManager em, int idUser){
        return em.createNamedQuery("UserEntity.SelectRolePermissionOfUser", RolePermissionEntity.class)
                .setParameter("idUser", idUser)
                .getResultList();
    }

}
