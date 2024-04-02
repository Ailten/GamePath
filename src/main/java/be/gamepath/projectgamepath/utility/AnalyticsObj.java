package be.gamepath.projectgamepath.utility;

import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsObj {

    public AnalyticsObj(String label, int quantity){
        this.label = label;
        this.quantity = quantity;
    }

    private String label;
    public String getLabel(){ return this.label; }
    public void setLabel(String label){ this.label = label; }

    private int quantity;
    public int getQuantity(){ return this.quantity; }
    public void setLabel(int quantity){ this.quantity = quantity; }


    //cast List<AnalyticsObj> to String of label concat.
    public static String castListToStringLabel(List<AnalyticsObj> listAnalyticsObj){
        return listAnalyticsObj.stream()
                .map(aObj -> "\""+aObj.getLabel()+"\"")
                .collect(Collectors.joining(", "));
    }

    //cast List<AnalyticsObj> to String of quantities concat.
    public static String castListToStringQuantity(List<AnalyticsObj> listAnalyticsObj){
        return listAnalyticsObj.stream()
                .map(aObj -> Integer.toString(aObj.getQuantity()))
                .collect(Collectors.joining(", "));
    }

    //get the biggest quantity in a List<AnalyticsObj>.
    public static int getListQuantityMax(List<AnalyticsObj> listAnalyticsObj){
        return listAnalyticsObj.stream()
                .map(AnalyticsObj::getQuantity)
                .max(Comparator.naturalOrder())
                .orElse(0);
    }


    //fill a List<AnalyticsObj> with days empty of the same month set on label (need a label dd/mm/YYYY).
    public static List<AnalyticsObj> fillListWithEmptyDayOfMonth(List<AnalyticsObj> listAnalyticsObj) throws Exception {
        if(listAnalyticsObj.size() == 0)
            return new ArrayList<>();

        String[] dateSplited = listAnalyticsObj.get(0).getLabel().split("/");
        if(dateSplited.length != 3){
            Utility.debug("error into AnalyticsObj.");
            throw new Exception("AnalyticsObj.label is not a date format !");
        }

        List<AnalyticsObj> out = new ArrayList<>();

        int day = 1;
        int month = Integer.parseInt(dateSplited[1])-1;
        int year = Integer.parseInt(dateSplited[2]);
        Date currentDate = Utility.makeDate(year, month, day);
        AnalyticsObj currentAObj;
        while(Utility.dateGetMonth(currentDate) == month){ //loop on every day of month.

            //build date string for find by label.
            day = Utility.dateGetDay(currentDate);
            String dateConcat = (
                    (day<10? "0": "")+day+"/"+
                    ((month+1)<10? "0": "")+(month+1)+"/"+
                    year
            );

            //find by label if an analytics obj is set.
            currentAObj = listAnalyticsObj.stream()
                    .filter(aObj -> aObj.getLabel().equals(dateConcat))
                    .findFirst().orElse(null);

            //push valid analytics obj or empty one.
            out.add(currentAObj != null? currentAObj: new AnalyticsObj(dateConcat, 0));

            //add day for loop increment.
            currentDate = Utility.dateAddDay(currentDate);
        }

        return out;
    }

}
