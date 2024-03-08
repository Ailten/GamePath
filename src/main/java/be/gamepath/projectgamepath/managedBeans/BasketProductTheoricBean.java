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

        EntityManager em = EMF.getEM();
        BasketProductTheoricService basketProductTheoricService = new BasketProductTheoricService();
        BasketService basketService = new BasketService();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();

            //get basket of user (or create it).
            Basket basket = basketService.selectByIdUser(em, connectionBean.getUser().getId());
            if(basket == null){
                basket = new Basket();
                basket.setUser(connectionBean.getUser());
                basket = basketService.insert(em, basket);
            }

            //insert basketProductTheoric.
            this.elementCrudSelected = new BasketProductTheoric();
            this.elementCrudSelected.setBasket(basket);
            this.elementCrudSelected.setProductTheoric(productTheoric);
            basketProductTheoricService.insert(em, this.elementCrudSelected);

            //success pop-up.
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.titleSuccess"),
                    Utility.stringFromI18N("application.crudPage.messageSuccess"),
                    true
            );

            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
            Utility.debug("error into addBasket : " + e.getMessage());
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.errorTitleInsert"),
                    Utility.stringFromI18N("application.crudPage.errorInsert"),
                    false
            );
        }finally{
            if(transaction.isActive()) //last security.
                transaction.rollback();
            em.close();
        }

    }


    public void delete(Basket basket, ProductTheoric productTheoric){

        //TODO: em, select basketProductTheoric from DB. if null error. delete it. remove from list basket. if list size = 0 remove basket.

        EntityManager em = EMF.getEM();
        BasketProductTheoricService basketProductTheoricService = new BasketProductTheoricService();
        BasketService basketService = new BasketService();
        EntityTransaction transaction = em.getTransaction();

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
            basketProductTheoricService.delete(em, this.elementCrudSelected);
            this.elementCrudSelected = null;

            //re-load list productTheoric transient from DB (without element deleted).
            BasketBean.initListProductTheoric(basketBean.getElementCrudSelected());

            //if basket empty, delete basket.
            if(basketBean.getElementCrudSelected().getListProductTheoric().size() == 0){
                basketService.delete(em, basketBean.getElementCrudSelected());
                basketBean.setElementCrudSelected(null);
            }

            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.successTitleDelete"),
                    Utility.stringFromI18N("application.crudPage.successDelete"),
                    true
            );

            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
            Utility.debug("error into delete : " + e.getMessage());
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.errorTitleDelete"),
                    Utility.stringFromI18N("application.crudPage.errorDelete"),
                    false
            );
        }finally{
            if(transaction.isActive()) //last security.
                transaction.rollback();
            em.close();
        }

    }

}
