package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.SocietyProducer;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class SocietyProducerService extends ServiceGeneric<SocietyProducer> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public SocietyProducer selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("SocietyProducer.SelectById", SocietyProducer.class)
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
    public List<SocietyProducer> selectMany(EntityManager em)
    {
        return em.createNamedQuery("SocietyProducer.SelectMany", SocietyProducer.class)
                .getResultList();
    }

}
