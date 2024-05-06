package be.gamepath.projectgamepath.service;

import be.gamepath.projectgamepath.entities.Order;
import be.gamepath.projectgamepath.utility.AnalyticsObj;
import be.gamepath.projectgamepath.utility.ServiceGeneric;
import be.gamepath.projectgamepath.utility.Utility;
import org.primefaces.shaded.json.JSONObject;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

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

    /**
     * select many order from DB, by filter.
     * @param em EntityManager.
     * @param filter string filter.
     * @param filterDate date filter.
     * @return list of Order.
     */
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
     * @param filterDate date (for filter by month).
     * @return list of json object {label:"productTheoric.title", quantity:"count()"}.
     */
    public static List<AnalyticsObj> selectBestSellAnalytics(EntityManager em, Date filterDate){
        Date filterDateNextMonth = null;
        if(filterDate != null){
            filterDateNextMonth = new Date(filterDate.getYear(),filterDate.getMonth(),filterDate.getDate());
            filterDateNextMonth.setMonth(filterDateNextMonth.getMonth() + 1);
        }
        return em.createNamedQuery("Order.SelectBestSellAnalytics", Object[].class)
                .setParameter("filterDate", filterDate)
                .setParameter("filterDateNextMonth", filterDateNextMonth)
                .getResultStream().map(objectArray -> new AnalyticsObj((String)objectArray[0], (int)((long)objectArray[1])))
                .collect(Collectors.toList());
    }

    /**
     * get list of json object (for analytics graphics).
     * @param em entity manager.
     * @param filterDate date (for filter by month).
     * @return list of json object {label:"date day of month", quantity:"count()"}.
     */
    public static List<AnalyticsObj> selectStatsOfMonthAnalyticsData(EntityManager em, Date filterDate){
        Date filterDateNextMonth = null;
        if(filterDate != null){
            filterDateNextMonth = new Date(filterDate.getYear(),filterDate.getMonth(),filterDate.getDate());
            filterDateNextMonth.setMonth(filterDateNextMonth.getMonth() + 1);
        }
        return em.createNamedQuery("Order.SelectStatsOfMonthAnalyticsData", Object[].class)
                .setParameter("filterDate", filterDate)
                .setParameter("filterDateNextMonth", filterDateNextMonth)
                .getResultStream().map(objectArray -> new AnalyticsObj((String)objectArray[0], (int)((long)objectArray[1])))
                .collect(Collectors.toList());
    }

}
