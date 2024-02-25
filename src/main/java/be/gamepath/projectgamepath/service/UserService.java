package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.RolePermission;
import be.gamepath.projectgamepath.entities.User;

import javax.persistence.EntityManager;
import java.util.List;

public class UserService {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public User selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("User.SelectById", User.class)
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
    public List<User> selectMany(EntityManager em)
    {
        return em.createNamedQuery("User.SelectMany", User.class)
                .getResultList();
    }

    /**
     * insert an entity in db.
     * @param em entity manager.
     * @param entityToInsert entity to insert.
     * @return entity inserted.
     */
    public User insert(EntityManager em, User entityToInsert)
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
    public User update(EntityManager em, User entityToUpdate)
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
    public void delete(EntityManager em, User entityToDelete){
        if(!em.contains(entityToDelete))
            entityToDelete = em.merge(entityToDelete);
        em.remove(entityToDelete);
        em.flush();
    }

    public List<RolePermission> selectRolePermissionOfUser(EntityManager em, int idUser){
        return em.createNamedQuery("User.SelectRolePermissionOfUser", RolePermission.class)
                .setParameter("idUser", idUser)
                .getResultList();
    }

    public User selectUserByLogin(EntityManager em, String loginUser){
        return em.createNamedQuery("User.SelectUserByLogin", User.class)
                .setParameter("loginUser", loginUser)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
