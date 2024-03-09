package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.Order;
import be.gamepath.projectgamepath.service.OrderService;
import be.gamepath.projectgamepath.utility.TableFilter;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;

@Named
@SessionScoped
public class OrderListBean extends TableFilter<Order> implements Serializable {

    public void doResearch() {

        EntityManager em = EMF.getEM();
        OrderService orderService = new OrderService();

        try{
            if(connectionBean.isUserHasPermission("readList-order-all")) //get all orders of all users.
                this.entityFiltered = orderService.selectManyByFilter(em, this.filter);
            else //get orders of user connected.
                this.entityFiltered = orderService.selectManyByFilterOneUser(em, this.filter,
                        connectionBean.getUser().getId());
        }catch(Exception e){
            Utility.debug("error into doResearch : " + e.getMessage());
            this.entityFiltered = new ArrayList<>();
        }finally{
            em.close();
        }

    }

}
