package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.service.OrderService;
import be.gamepath.projectgamepath.utility.AnalyticsObj;
import be.gamepath.projectgamepath.utility.Utility;
import org.primefaces.shaded.json.JSONObject;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class AnalyticsBean implements Serializable {

    /**
     * get data for a graphics stats (from DB).
     * @return string json of data stats for chartJs.
     */
    public String getBestSellAnalyticsData(){

        EntityManager em = EMF.createEM();
        OrderService orderService = new OrderService();
        List<AnalyticsObj> dataGetFromDB;

        try{
            dataGetFromDB = orderService.selectBestSellAnalytics(
                    em,
                    this.filterDate
            );
        }catch(Exception e){
            Utility.debug("error into getBestSellAnalyticsData : " + e.getMessage());
            dataGetFromDB = new ArrayList<>();
        }finally{
            em.close();
        }

        //params apply in data json.
        String title = Utility.stringFromI18N("application.header.menuBestSellAnalytics");

        //object json data for chart js.
        JSONObject jsonData = new JSONObject();
        jsonData.put("type", "bar");
        jsonData.put("data", new JSONObject("{" +
                "labels: ["+AnalyticsObj.castListToStringLabel(dataGetFromDB)+"]," + //get data label in array string.
                "datasets: [{" +
                "    label: \""+title+"\"," +
                "    data: ["+AnalyticsObj.castListToStringQuantity(dataGetFromDB)+"]," + //get data quantity in array int.
                "    borderWidth: 1" +
                "}]" +
        "}"));
        jsonData.put("options", new JSONObject("{" +
                "scales: {" +
                "    y: {" +
                "        beginAtZero: true," +
                "        ticks: {" +
                "           maxTicksLimit: "+(AnalyticsObj.getListQuantityMax(dataGetFromDB)+1) + //get max value y (for range y one by one).
                "        }" +
                "    }" +
                "}" +
        "}"));
        return jsonData.toString();

    }

    /**
     * get data for a graphics stats (from DB).
     * @return string json of data stats for chartJs.
     */
    public String getStatsOfMonthAnalyticsData(){

        EntityManager em = EMF.createEM();
        OrderService orderService = new OrderService();
        List<AnalyticsObj> dataGetFromDB;

        try{
            Date filterDateOrMonthNow = (this.filterDate != null? this.filterDate:
                    Utility.makeDate(Utility.dateGetYear(new Date()), Utility.dateGetMonth(new Date()), 0)
            );

            dataGetFromDB = orderService.selectStatsOfMonthAnalyticsData(
                    em,
                    filterDateOrMonthNow
            );

            dataGetFromDB = AnalyticsObj.fillListWithEmptyDayOfMonth(dataGetFromDB);
        }catch(Exception e){
            Utility.debug("error into getBestSellAnalyticsData : " + e.getMessage());
            dataGetFromDB = new ArrayList<>();
        }finally{
            em.close();
        }

        //params apply in data json.
        String title = Utility.stringFromI18N("application.header.menuStatsOfMonthAnalytics");

        //object json data for chart js.
        JSONObject jsonData = new JSONObject();
        jsonData.put("type", "bar");
        jsonData.put("data", new JSONObject("{" +
                "labels: ["+AnalyticsObj.castListToStringLabel(dataGetFromDB)+"]," + //get data label in array string.
                "datasets: [{" +
                "    label: \""+title+"\"," +
                "    data: ["+AnalyticsObj.castListToStringQuantity(dataGetFromDB)+"]," + //get data quantity in array int.
                "    borderWidth: 1" +
                "}]" +
                "}"));
        jsonData.put("options", new JSONObject("{" +
                "scales: {" +
                "    y: {" +
                "        beginAtZero: true," +
                "        ticks: {" +
                "           maxTicksLimit: "+(AnalyticsObj.getListQuantityMax(dataGetFromDB)+1) + //get max value y (for range y one by one).
                "        }" +
                "    }" +
                "}" +
                "}"));
        return jsonData.toString();

    }


    @Temporal(TemporalType.DATE)
    private Date filterDate = null;
    public Date getFilterDate() {
        return filterDate;
    }
    public void setFilterDate(Date filterDate) {
        this.filterDate = filterDate;
    }

}
