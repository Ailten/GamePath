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


    @Pattern(regexp = "^[a-zA-Z0-9 çéâêîôûàèìòùëïü.]{0,60}$")
    protected String filter = "";
    public void setFilter(String filter) {
        this.filter = filter.toLowerCase();
    }
    public String getFilter(){
        return this.filter;
    }


    //reset filter.
    public void resetFilter(){
        this.filter = "";
    }

}
