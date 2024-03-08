package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.entities.Pegi;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class PegiService extends ServiceGeneric<Pegi> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public Pegi selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("Pegi.SelectById", Pegi.class)
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
    public List<Pegi> selectMany(EntityManager em)
    {
        return em.createNamedQuery("Pegi.SelectMany", Pegi.class)
                .getResultList();
    }



    public List<Pegi> selectManyByIdProductTheoric(EntityManager em, int idProductTheoric) {
        return em.createNamedQuery("Pegi.SelectManyByIdProduct", Pegi.class)
                .setParameter("idProductTheoric", idProductTheoric)
                .getResultList();
    }

}
