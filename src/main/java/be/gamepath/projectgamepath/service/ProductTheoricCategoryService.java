package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Permission;
import be.gamepath.projectgamepath.entities.ProductTheoricCategory;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class ProductTheoricCategoryService extends ServiceGeneric<ProductTheoricCategory> {

    //no need to override selectById and selectMany for join table.

    /**
     * select an element by sending two foreign key.
     * @param em EntityManager.
     * @param idProductTheoric first foreign key.
     * @param idCategory second foreign key.
     * @return a ProductTheoricCategory (or null).
     */
    public ProductTheoricCategory selectByBothId(EntityManager em, int idProductTheoric, int idCategory) {
        return em.createNamedQuery("ProductTheoricCategory.SelectByBothId", ProductTheoricCategory.class)
                .setParameter("idProductTheoric", idProductTheoric)
                .setParameter("idCategory", idCategory)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
