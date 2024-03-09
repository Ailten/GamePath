package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.*;
import be.gamepath.projectgamepath.enumeration.Crud;
import be.gamepath.projectgamepath.service.*;
import be.gamepath.projectgamepath.utility.CrudManaging;
import be.gamepath.projectgamepath.utility.FileManaging;
import be.gamepath.projectgamepath.utility.MailManager;
import be.gamepath.projectgamepath.utility.Utility;
import org.eclipse.persistence.internal.jpa.transaction.EntityTransactionImpl;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
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

        if(!connectionBean.isUserHasPermission(Crud.CREATE.getTxtValue() + "-order")){
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.titleErrorNoPermission"),
                    Utility.stringFromI18N("application.crudPage.messageErrorNoPermission"),
                    false
            );
            return null;
        }

        EntityManager em = EMF.getEM();
        OrderService orderService = new OrderService();
        ProductKeyService productKeyService = new ProductKeyService();
        BasketProductTheoricService basketProductTheoricService = new BasketProductTheoricService();
        BasketService basketService = new BasketService();
        UserProductTheoricService userProductTheoricService = new UserProductTheoricService();
        EntityTransaction transaction = em.getTransaction();
        boolean isSuccess = false;

        try{
            transaction.begin();

            //make order.
            Order order = new Order();
            order.setUser(this.elementCrudSelected.getUser());
            order.setPayementType(this.elementCrudSelected.getPayementType());
            order.setValidateBasketDate(Utility.castLocalDateTimeToDate(LocalDateTime.now()));
            order.setIsForMe(this.elementCrudSelected.getIsForMe());

            //insert order.
            order = orderService.insert(em, order);

            //loop on every product in basket for add to order.
            ProductKey currentProducKey;
            order.setListProductKey(new ArrayList<>());
            for(ProductTheoric product : this.elementCrudSelected.getListProductTheoric()){
                currentProducKey = new ProductKey();
                currentProducKey.setOrder(order);
                currentProducKey.setProductTheoric(product);
                currentProducKey.setKey("000000-000000-000000"); //set fake key placeholder.
                currentProducKey.setCurrentPriceHtva(product.getPriceHtva());
                currentProducKey.setCurrentTva(product.getTva());
                currentProducKey.setCurrentReduction(product.getReduction());
                currentProducKey.setIsValid(true);

                //insert productKey.
                currentProducKey = productKeyService.insert(em, currentProducKey);

                //generate key.
                currentProducKey.generateKey();

                //auto assign to my library.
                if(this.elementCrudSelected.getIsForMe()){
                    currentProducKey.setIsValid(false);

                    //verify if user already has product.
                    UserProductTheoric userProductTheoric = userProductTheoricService.selectByBothId(em,
                            connectionBean.getUser().getId(), currentProducKey.getProductTheoric().getId());
                    if(userProductTheoric != null){
                        popUpMessageBean.setPopUpMessage(
                                Utility.stringFromI18N("application.crudUserProductTheoric.errorTitleAlreadyHaveProduct"),
                                Utility.stringFromI18N("application.crudUserProductTheoric.errorMessageAlreadyHaveProduct"),
                                false
                        );
                        throw new Exception("error, user already has this product.");
                    }

                    //create userProduct.
                    userProductTheoric = new UserProductTheoric();
                    userProductTheoric.setUser(connectionBean.getUser());
                    userProductTheoric.setProductTheoric(currentProducKey.getProductTheoric());
                    userProductTheoric.setKeyUsed(currentProducKey.getKey());
                    userProductTheoric.setUnlockDate(Utility.castLocalDateTimeToDate(LocalDateTime.now()));
                    userProductTheoricService.insert(em, userProductTheoric);
                }

                //update productKey (for key generated).
                currentProducKey = productKeyService.update(em, currentProducKey);

                //push in list transient.
                order.getListProductKey().add(currentProducKey);
            }

            //if order make successfully, delete basket and his joins.
            for(ProductTheoric product : this.elementCrudSelected.getListProductTheoric()){
                basketProductTheoricService.delete(em,
                        basketProductTheoricService.selectByBothId(em,
                                this.elementCrudSelected.getId(), product.getId()));
            }
            basketService.delete(em, this.elementCrudSelected);
            this.elementCrudSelected = null;

            if(order.getId() != 0)
                throw new Exception("fake error");

            //create PDF file.
            String pathPDF = FileManaging.createPDFOrder(order);

            //email to the user.
            MailManager.sendMail(
                    order.getUser().getEmail(),
                    "Votre commande",
                    "Votre commande à bien été passée ! \n\n" +
                            "Vous trouverez ci-dessous les détailles de votre commande. \n\n" +
                            "L'équipe de GamePath vous remercie.",
                    pathPDF
            );

            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.basket.titleSuccessSubmit"),
                    Utility.stringFromI18N("application.basket.messageSuccessSubmit"),
                    true
            );

            isSuccess = true;
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
            Utility.debug("error into submitBasket : " + e.getMessage());
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.basket.titleErrorSubmit"),
                    Utility.stringFromI18N("application.basket.messageErrorSubmit"),
                    false
            );
            isSuccess = false;
        }finally{
            if(transaction.isActive()) //last security.
                transaction.rollback();
            em.close();
        }

        if(!isSuccess)
            return null;
        //if(this.elementCrudSelected.getIsForMe()) //idee, for redirect to my library if order for me.
        //    return "";
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
