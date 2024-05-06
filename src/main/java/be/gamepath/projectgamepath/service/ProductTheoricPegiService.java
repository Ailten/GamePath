package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.ProductTheoricCategory;
import be.gamepath.projectgamepath.entities.ProductTheoricPegi;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class ProductTheoricPegiService extends ServiceGeneric<ProductTheoricPegi> {

    //no need to override selectById and selectMany for join table.

    /**
     * select an element by sending two foreign key.
     * @param em EntityManager.
     * @param idProductTheoric first foreign key.
     * @param idPegi second foreign key.
     * @return a ProductTheoricPegi (or null).
     */
    public ProductTheoricPegi selectByBothId(EntityManager em, int idProductTheoric, int idPegi) {
        return em.createNamedQuery("ProductTheoricPegi.SelectByBothId", ProductTheoricPegi.class)
                .setParameter("idProductTheoric", idProductTheoric)
                .setParameter("idPegi", idPegi)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
