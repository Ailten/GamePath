package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.Order;
import be.gamepath.projectgamepath.service.ProductKeyService;
import be.gamepath.projectgamepath.utility.CrudManaging;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;

@Named
@SessionScoped
public class OrderBean extends CrudManaging<Order> implements Serializable {

    //set name table for permission concatenation.
    public OrderBean(){
        this.nameEntityForPermission = "order"; //send name of entity for check permission generic.
    }

    //overide getter, if has no basket, use a fake basket empty.
    public Order getElementCrudSelected(){
        if(this.elementCrudSelected == null)
            this.elementCrudSelected = new Order();
        return this.elementCrudSelected;
    }


    /**
     * Initialize list productKey into an order
     */
    public static void initListProductKey(Order order) {
        EntityManager em = EMF.createEM();
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
