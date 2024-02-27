package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Role;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import java.util.List;

public class RoleService extends ServiceGeneric<Role> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public Role selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("Role.SelectById", Role.class)
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
    public List<Role> selectMany(EntityManager em)
    {
        return em.createNamedQuery("Role.SelectMany", Role.class)
                .getResultList();
    }


}
