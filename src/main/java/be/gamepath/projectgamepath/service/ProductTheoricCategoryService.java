package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Permission;
import be.gamepath.projectgamepath.entities.ProductTheoricCategory;
import be.gamepath.projectgamepath.utility.ServiceGeneric;

import javax.persistence.EntityManager;

public class ProductTheoricCategoryService extends ServiceGeneric<ProductTheoricCategory> {

    //no need to override selectById and selectMany for join table.

    public ProductTheoricCategory selectByBothId(EntityManager em, int idProductTheoric, int idCategory) {
        return em.createNamedQuery("ProductTheoricCategory.SelectByBothId", ProductTheoricCategory.class)
                .setParameter("idProductTheoric", idProductTheoric)
                .setParameter("idCategory", idCategory)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
