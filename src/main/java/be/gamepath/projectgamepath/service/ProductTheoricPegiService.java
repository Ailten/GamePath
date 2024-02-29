package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.ProductTheoricCategory;
import be.gamepath.projectgamepath.entities.ProductTheoricPegi;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;

public class ProductTheoricPegiService extends ServiceGeneric<ProductTheoricPegi> {

    //no need to override selectById and selectMany for join table.

    public ProductTheoricPegi selectByBothId(EntityManager em, int idProductTheoric, int idPegi) {
        return em.createNamedQuery("ProductTheoricPegi.SelectByBothId", ProductTheoricPegi.class)
                .setParameter("idProductTheoric", idProductTheoric)
                .setParameter("idPegi", idPegi)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
