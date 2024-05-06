package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.Order;
import be.gamepath.projectgamepath.service.OrderService;
import be.gamepath.projectgamepath.utility.TableFilter;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Named
@SessionScoped
public class OrderListBean extends TableFilter<Order> implements Serializable {

    /**
     * make research in order table (from DB), affect list in parent.
     */
    public void doResearch() {

        EntityManager em = EMF.createEM();
        OrderService orderService = new OrderService();

        try{
            if(connectionBean.isUserHasPermission("readList-order-all")) //get all orders of all users.
                this.entityFiltered = orderService.selectManyByFilter(em, this.filter, this.filterDate);
            else //get orders of user connected.
                this.entityFiltered = orderService.selectManyByFilterOneUser(em, this.filter, this.filterDate,
                        connectionBean.getUser().getId());
        }catch(Exception e){
            Utility.debug("error into doResearch : " + e.getMessage());
            this.entityFiltered = new ArrayList<>();
        }finally{
            em.close();
        }

    }


    @Temporal(TemporalType.DATE)
    private Date filterDate = null;
    public Date getFilterDate() {
        return filterDate;
    }
    public void setFilterDate(Date filterDate) {
        this.filterDate = filterDate;
    }


    public String rangeYearFilter() {
        int yearNow = new Date().getYear() +1900;
        int rangeYearFromNow = 10;
        return ((yearNow-(rangeYearFromNow))+":"+(yearNow+(rangeYearFromNow)));
    }

}
