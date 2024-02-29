package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Permission;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import java.util.List;

public class PermissionService extends ServiceGeneric<Permission> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public Permission selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("Permission.SelectById", Permission.class)
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
    public List<Permission> selectMany(EntityManager em)
    {
        return em.createNamedQuery("Permission.SelectMany", Permission.class)
                .getResultList();
    }


    public List<Permission> selectManyByIdRole(EntityManager em, int idRole){
        return em.createNamedQuery("Permission.SelectManyByIdRole", Permission.class)
                .setParameter("idRole", idRole)
                .getResultList();
    }

}
