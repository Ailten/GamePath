package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.Basket;
import be.gamepath.projectgamepath.entities.ProductTheoric;
import be.gamepath.projectgamepath.enumeration.Crud;
import be.gamepath.projectgamepath.service.BasketProductTheoricService;
import be.gamepath.projectgamepath.service.BasketService;
import be.gamepath.projectgamepath.service.PictureProductService;
import be.gamepath.projectgamepath.service.ProductTheoricService;
import be.gamepath.projectgamepath.utility.CrudManaging;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;

@Named
@SessionScoped
public class BasketBean extends CrudManaging<Basket> implements Serializable {

    public BasketBean(){
        this.nameEntityForPermission = "basket"; //send name of entity for check permission generic.
    }

    //overide getter, if has no basket, use a fake basket empty.
    public Basket getElementCrudSelected(){
        if(this.elementCrudSelected == null)
            this.elementCrudSelected = new Basket();
        return this.elementCrudSelected;
    }


    /**
     * Initialize list productTheoric into a basket
     */
    public static void initListProductTheoric(Basket basket) {

        EntityManager em = EMF.getEM();
        ProductTheoricService productTheoricService = new ProductTheoricService();

        try{
            basket.setListProductTheoric((basket.getId() != 0)?
                    productTheoricService.selectManyByIdBasket(em, basket.getId()):
                    new ArrayList<>() //fake basket, so list empty.
            );
        }catch(Exception e){
            Utility.debug("error into initListProductTheoric : " + e.getMessage());
            basket.setListProductTheoric(new ArrayList<>());
        }finally{
            em.close();
        }
    }


    public String submitBasket(){

        //TODO: create order, delete basket, redirect page home (+ do verification).

        return connectionBean.getPathHomePage();
    }


    //load basket from DB (if is a fake basket empty)
    public void loadBasketFromDB(){

        if(this.getElementCrudSelected().getId() != 0)
            return; //basket is already load.

        EntityManager em = EMF.getEM();
        BasketService basketService = new BasketService();

        try{
            this.elementCrudSelected = basketService.selectByIdUser(em, connectionBean.getUser().getId());
        }catch(Exception e){
            Utility.debug("error into loadBasketFromDB : " + e.getMessage());
        }finally{
            em.close();
        }

    }


}
