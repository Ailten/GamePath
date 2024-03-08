package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.entities.Language;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class LanguageService extends ServiceGeneric<Language> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public Language selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("Language.SelectById", Language.class)
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
    public List<Language> selectMany(EntityManager em)
    {
        return em.createNamedQuery("Language.SelectMany", Language.class)
                .getResultList();
    }


    public List<Language> selectManyByIdProductTheoric(EntityManager em, int idProductTheoric) {
        return em.createNamedQuery("Language.SelectManyByIdProduct", Language.class)
                .setParameter("idProductTheoric", idProductTheoric)
                .getResultList();
    }

}
