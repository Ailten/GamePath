package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.Order;
import be.gamepath.projectgamepath.entities.ProductKey;
import be.gamepath.projectgamepath.entities.ProductTheoric;
import be.gamepath.projectgamepath.service.CategoryService;
import be.gamepath.projectgamepath.service.ProductKeyService;
import be.gamepath.projectgamepath.utility.Utility;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class OrderBean {

    /**
     * Initialize list productKey into an order
     */
    public static void initListProductKey(Order order) {
        EntityManager em = EMF.getEM();
        ProductKeyService productKeyService = new ProductKeyService();

        try{
            order.setListProductKey(
                    productKeyService.selectManyByIdOrder(em, order.getId())
            );
        }catch(Exception e){
            Utility.debug("error into initProductKey : " + e.getMessage());
            order.setListProductKey(new ArrayList<>());
        }finally{
            em.close();
        }
    }

}
