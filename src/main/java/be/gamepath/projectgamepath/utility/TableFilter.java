package be.gamepath.projectgamepath.utility;

import be.gamepath.projectgamepath.managedBeans.ConnectionBean;
import org.primefaces.PrimeFaces;

import be.gamepath.projectgamepath.connexion.EMF;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

public class TableFilter<TEntity extends EntityGenerique> {

    @Inject
    protected ConnectionBean connectionBean;


    //entity filtered from db.
    protected List<TEntity> entityFiltered;
    public List<TEntity> getEntityFiltered(){
        return this.entityFiltered;
    }
    public void setEntityFiltered(List<TEntity> entityFiltered){
        this.entityFiltered = entityFiltered;
    }



    /**
     * make a research in db for list entity with filter.
     */
    public void doResearch() throws Exception {
        throw new Exception("TableFilter.doResearch was not override");
    }


    /*
    protected String order = "id";
    protected boolean orderAsc = false;
    public void editOrderTable(String order){

        if(this.order.equals(order)){
            orderAsc = !orderAsc;
        }else{
            orderAsc = true;
            this.order = order;
        }

    }
    //public String getOrderIcon(String order){
    //    if(!this.order.equals(order))
    //        return "pi pi-circle-off";
    //    return ((orderAsc)? "pi pi-chevron-circle-down": "pi pi-chevron-circle-up");
    //}
    */



    @Pattern(regexp = "^[a-zA-Z0-9 çéâêîôûàèìòùëïü.]{0,60}$")
    protected String filter = "";
    public void setFilter(String filter) {
        this.filter = filter.toLowerCase();
    }
    public String getFilter(){
        return this.filter;
    }



    //call JS for apply class research word in a table list.
    //public void applyResearchWordClass(){
    //    PrimeFaces.current().executeScript("applyClassWordResearch(\""+this.filter+"\")");
    //}



    //reset filter (call before an insert db, for apply last insert on the top of list).
    public void resetFilter(){
        //this.order = "id";
        //this.orderAsc = false;
        this.filter = "";
    }

}
