package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.ProductTheoricCategory;
import be.gamepath.projectgamepath.entities.ProductTheoricLanguage;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;

public class ProductTheoricLanguageService extends ServiceGeneric<ProductTheoricLanguage> {

    //no need to override selectById and selectMany for join table.

    public ProductTheoricLanguage selectByBothId(EntityManager em, int idProductTheoric, int idLanguage) {
        return em.createNamedQuery("ProductTheoricLanguage.SelectByBothId", ProductTheoricLanguage.class)
                .setParameter("idProductTheoric", idProductTheoric)
                .setParameter("idLanguage", idLanguage)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
