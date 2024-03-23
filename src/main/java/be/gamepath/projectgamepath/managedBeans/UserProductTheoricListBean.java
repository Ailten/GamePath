package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.UserProductTheoric;
import be.gamepath.projectgamepath.service.UserProductTheoricService;
import be.gamepath.projectgamepath.utility.TableFilter;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;

@Named
@SessionScoped
public class UserProductTheoricListBean extends TableFilter<UserProductTheoric> implements Serializable {

    public void doResearch() {

        EntityManager em = EMF.createEM();
        UserProductTheoricService userProductTheoricService = new UserProductTheoricService();

        try{
            this.entityFiltered = userProductTheoricService.selectManyByFilter(em, this.filter,
                    connectionBean.getUser().getId());
        }catch(Exception e){
            Utility.debug("error into doResearch : " + e.getMessage());
            this.entityFiltered = new ArrayList<>();
        }finally{
            em.close();
        }

    }

}
