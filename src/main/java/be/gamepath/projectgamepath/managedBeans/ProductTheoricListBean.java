package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.ProductTheoric;
import be.gamepath.projectgamepath.service.ProductTheoricService;
import be.gamepath.projectgamepath.utility.TableFilter;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;

@Named
@SessionScoped
public class ProductTheoricListBean extends TableFilter<ProductTheoric> implements Serializable {

    @Inject
    ConnectionBean connectionBean;

    public void doResearch() {

        EntityManager em = EMF.getEM();
        ProductTheoricService productTheoricService = new ProductTheoricService();

        try{
            this.entityFiltered = productTheoricService.selectManyByFilter(em, this.filter,
                    connectionBean.isUserHasPermission("readList-product-disable"));
        }catch(Exception e){
            Utility.debug("error into doResearch : " + e.getMessage());
            this.entityFiltered = new ArrayList<>();
        }finally{
            em.close();
        }

    }

}
