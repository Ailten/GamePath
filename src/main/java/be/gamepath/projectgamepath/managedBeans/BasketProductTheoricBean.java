package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.Basket;
import be.gamepath.projectgamepath.entities.BasketProductTheoric;
import be.gamepath.projectgamepath.entities.ProductTheoric;
import be.gamepath.projectgamepath.enumeration.Crud;
import be.gamepath.projectgamepath.service.BasketProductTheoricService;
import be.gamepath.projectgamepath.service.BasketService;
import be.gamepath.projectgamepath.utility.CrudManaging;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class BasketProductTheoricBean extends CrudManaging<BasketProductTheoric> implements Serializable {

    @Inject
    BasketBean basketBean;

    public BasketProductTheoricBean(){
        this.nameEntityForPermission = "basketproduct"; //send name of entity for check permission generic.
    }

    public void addBasket(ProductTheoric productTheoric){

        if(!connectionBean.isUserHasPermission(Crud.CREATE.getTxtValue() + "-" + this.nameEntityForPermission)){
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.titleErrorNoPermission"),
                    Utility.stringFromI18N("application.crudPage.messageErrorNoPermission"),
                    false
            );
            return;
        }

        EntityManager em = EMF.createEM();
        BasketProductTheoricService basketProductTheoricService = new BasketProductTheoricService();
        BasketService basketService = new BasketService();
        EntityTransaction transaction = EMF.getTransaction(em);

        try{
            transaction.begin();

            //get basket of user (or create it).
            Basket basket = basketService.selectByIdUser(em, connectionBean.getUser().getId());
            if(basket == null){

                //make basket.
                basket = new Basket();
                basket.setUser(connectionBean.getUser());

                //add product (in cascade list).
                basket.setListBasketProductTheoric(new ArrayList<>());
                this.elementCrudSelected = new BasketProductTheoric();
                this.elementCrudSelected.setBasket(basket);
                this.elementCrudSelected.setProductTheoric(productTheoric);
                basket.getListBasketProductTheoric().add(this.elementCrudSelected);

                //insert basket.
                basketService.insert(em, basket);

            }else{

                //insert basketProductTheoric.
                this.elementCrudSelected = new BasketProductTheoric();
                this.elementCrudSelected.setBasket(basket);
                this.elementCrudSelected.setProductTheoric(productTheoric);
                basketProductTheoricService.insert(em, this.elementCrudSelected);

            }

            //success pop-up.
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.titleSuccess"),
                    Utility.stringFromI18N("application.crudPage.messageSuccess"),
                    true
            );

            EMF.transactionCommit(em, transaction);
        }catch(Exception e){
            EMF.transactionRollback(em, transaction);
            Utility.debug("error into addBasket : " + e.getMessage());
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.errorTitleInsert"),
                    Utility.stringFromI18N("application.crudPage.errorInsert"),
                    false
            );
        }finally{
            if(transaction.isActive()) //last security.
                EMF.transactionRollback(em, transaction);
            em.close();
        }

    }


    public void delete(Basket basket, ProductTheoric productTheoric){

        EntityManager em = EMF.createEM();
        BasketProductTheoricService basketProductTheoricService = new BasketProductTheoricService();
        BasketService basketService = new BasketService();
        EntityTransaction transaction = EMF.getTransaction(em);

        try{
            transaction.begin();

            //get basketProductTheoric concerned.
            this.elementCrudSelected = basketProductTheoricService.selectByBothId(em, basket.getId(), productTheoric.getId());
            if(this.elementCrudSelected == null){
                //reload list from DB.
                BasketBean.initListProductTheoric(basketBean.getElementCrudSelected());
                //popUpMessageBean.setPopUpMessage(
                //        Utility.stringFromI18N("application.basket.errorProductNotFound"),
                //        Utility.stringFromI18N("application.basket.errorMessageProductNotFound"),
                //        false
                //);
                throw new Exception("basketProductTheoric not found");
            }

            //delete basketProductTheoric.
            int idProductTheoric = this.elementCrudSelected.getProductTheoric().getId();
            basketProductTheoricService.delete(em, this.elementCrudSelected);
            this.elementCrudSelected = null;

            //update list productTheoric transient (without element deleted).
            basket.setListProductTheoric(
                    basket.getListProductTheoric().stream()
                            .filter(pt -> pt.getId()!=idProductTheoric).collect(Collectors.toList())
            );

            //if basket empty, delete basket.
            if(basket.getListProductTheoric().size() == 0){
                boolean isBasketCrudSelected = (basketBean.getElementCrudSelected().getId() == basket.getId());
                basketService.delete(em, basket);
                if(isBasketCrudSelected)
                    basketBean.setElementCrudSelected(null);
            }

            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.successTitleDelete"),
                    Utility.stringFromI18N("application.crudPage.successDelete"),
                    true
            );

            EMF.transactionCommit(em, transaction);
        }catch(Exception e){
            EMF.transactionRollback(em, transaction);
            Utility.debug("error into delete : " + e.getMessage());
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.errorTitleDelete"),
                    Utility.stringFromI18N("application.crudPage.errorDelete"),
                    false
            );
        }finally{
            if(transaction.isActive()) //last security.
                EMF.transactionRollback(em, transaction);
            em.close();
        }

    }

}
