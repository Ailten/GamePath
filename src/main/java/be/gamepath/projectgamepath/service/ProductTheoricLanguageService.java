package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.ProductTheoricCategory;
import be.gamepath.projectgamepath.entities.ProductTheoricLanguage;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class ProductTheoricLanguageService extends ServiceGeneric<ProductTheoricLanguage> {

    //no need to override selectById and selectMany for join table.

    /**
     * select an element by sending two foreign key.
     * @param em EntityManager.
     * @param idProductTheoric first foreign key.
     * @param idLanguage second foreign key.
     * @return a ProductTheoricLanguage (or null).
     */
    public ProductTheoricLanguage selectByBothId(EntityManager em, int idProductTheoric, int idLanguage) {
        return em.createNamedQuery("ProductTheoricLanguage.SelectByBothId", ProductTheoricLanguage.class)
                .setParameter("idProductTheoric", idProductTheoric)
                .setParameter("idLanguage", idLanguage)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
