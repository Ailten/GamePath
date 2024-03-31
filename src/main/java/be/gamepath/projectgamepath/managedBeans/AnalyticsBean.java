package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.service.OrderService;
import be.gamepath.projectgamepath.utility.Utility;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class AnalyticsBean implements Serializable {

    public String getBestSellAnalyticsData(){

        EntityManager em = EMF.createEM();
        OrderService orderService = new OrderService();
        List<JSONObject> dataGetFromDB;

        try{
            dataGetFromDB = orderService.selectBestSellAnalytics(em);
        }catch(Exception e){
            Utility.debug("error into getBestSellAnalyticsData : " + e.getMessage());
            dataGetFromDB = new ArrayList<>();
        }finally{
            em.close();
        }

        //params apply in data json.
        String title = Utility.stringFromI18N("application.header.menuBestSellAnalytics");
        JSONArray labels = new JSONArray();
        JSONArray quantity = new JSONArray();
        for(JSONObject row : dataGetFromDB){
            labels.put(row.get("label"));
            quantity.put(row.get("quantity"));
        }

        //object json data for chart js.
        JSONObject jsonData = new JSONObject();
        jsonData.put("type", "bar");
        jsonData.put("data", new JSONObject("{" +
                "labels: "+labels.toString()+"," +
                "datasets: [{" +
                "    label: \""+title+"\"," +
                "    data: "+quantity.toString()+"," +
                "    borderWidth: 1" +
                "}]" +
        "}"));
        jsonData.put("options", new JSONObject("{" +
                "scales: {" +
                "    y: {" +
                "        beginAtZero: true" +
                "    }" +
                "}" +
        "}"));
        return jsonData.toString();

    }

}
