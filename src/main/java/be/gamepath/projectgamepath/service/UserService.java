package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.RolePermission;
import be.gamepath.projectgamepath.entities.User;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class UserService extends ServiceGeneric<User> {

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
     * select an user from DB, by login.
     * @param em EntityManager.
     * @param loginUser string login user.
     * @return an User (or null).
     */
    public User selectUserByLogin(EntityManager em, String loginUser){
        return em.createNamedQuery("User.SelectUserByLogin", User.class)
                .setParameter("loginUser", loginUser)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
