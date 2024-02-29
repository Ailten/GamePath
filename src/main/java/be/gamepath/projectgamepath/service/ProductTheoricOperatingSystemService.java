package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.ProductTheoricCategory;
import be.gamepath.projectgamepath.entities.ProductTheoricOperatingSystem;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;

public class ProductTheoricOperatingSystemService extends ServiceGeneric<ProductTheoricOperatingSystem> {

    //no need to override selectById and selectMany for join table.

    public ProductTheoricOperatingSystem selectByBothId(EntityManager em, int idProductTheoric, int idOperatingSystem) {
        return em.createNamedQuery("ProductTheoricOperatingSystem.SelectByBothId", ProductTheoricOperatingSystem.class)
                .setParameter("idProductTheoric", idProductTheoric)
                .setParameter("idOperatingSystem", idOperatingSystem)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
