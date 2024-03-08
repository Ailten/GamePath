package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.entities.OperatingSystem;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class OperatingSystemService extends ServiceGeneric<OperatingSystem> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public OperatingSystem selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("OperatingSystem.SelectById", OperatingSystem.class)
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
    public List<OperatingSystem> selectMany(EntityManager em)
    {
        return em.createNamedQuery("OperatingSystem.SelectMany", OperatingSystem.class)
                .getResultList();
    }


    public List<OperatingSystem> selectManyByIdProductTheoric(EntityManager em, int idProductTheoric) {
        return em.createNamedQuery("OperatingSystem.SelectManyByIdProduct", OperatingSystem.class)
                .setParameter("idProductTheoric", idProductTheoric)
                .getResultList();
    }


}
