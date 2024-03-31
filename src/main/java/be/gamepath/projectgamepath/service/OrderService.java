package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Order;
import be.gamepath.projectgamepath.utility.ServiceGeneric;
import be.gamepath.projectgamepath.utility.Utility;
import org.primefaces.shaded.json.JSONObject;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class OrderService extends ServiceGeneric<Order> {

    /**
     * get one entity (find by primary key).
     * @param em entity manager.
     * @param id primary key of entity.
     * @return entity find.
     */
    public Order selectById(EntityManager em, int id)
    {
        return em.createNamedQuery("Order.SelectById", Order.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    /**
     * get list of entity.
     * @param em entity manager.
     * @return list entity match.
     */
    public List<Order> selectMany(EntityManager em)
    {
        return em.createNamedQuery("Order.SelectMany", Order.class)
                .getResultList();
    }


    public List<Order> selectManyByFilter(EntityManager em, String filter, Date filterDate){
        Date filterDateNextMonth = null;
        if(filterDate != null){
            filterDateNextMonth = new Date(filterDate.getYear(),filterDate.getMonth(),filterDate.getDate());
            filterDateNextMonth.setMonth(filterDateNextMonth.getMonth() + 1);
        }
        return em.createNamedQuery("Order.SelectManyByFilter", Order.class)
                .setParameter("filter", filter)
                .setParameter("filterDate", filterDate)
                .setParameter("filterDateNextMonth", filterDateNextMonth)
                .getResultList();
    }
    public List<Order> selectManyByFilterOneUser(EntityManager em, String filter, Date filterDate, int idUser){
        Date filterDateNextMonth = null;
        if(filterDate != null){
            filterDateNextMonth = new Date(filterDate.getYear(),filterDate.getMonth(),filterDate.getDate());
            filterDateNextMonth.setMonth(filterDateNextMonth.getMonth() + 1);
        }
        return em.createNamedQuery("Order.SelectManyByFilterOneUser", Order.class)
                .setParameter("filter", filter)
                .setParameter("filterDate", filterDate)
                .setParameter("filterDateNextMonth", filterDateNextMonth)
                .setParameter("idUser", idUser)
                .getResultList();
    }


    /**
     * get list of json object (for analytics graphics).
     * @param em entity manager.
     * @return list of json object {label:"productTheoric.title", quantity:"count()"}.
     */
    public static List<JSONObject> selectBestSellAnalytics(EntityManager em){
        return em.createNamedQuery("Order.SelectBestSellAnalytics", JSONObject.class)
                .getResultList();
    }


}
