package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.*;
import be.gamepath.projectgamepath.enumeration.Crud;
import be.gamepath.projectgamepath.service.*;
import be.gamepath.projectgamepath.utility.CrudManaging;
import be.gamepath.projectgamepath.utility.FileManaging;
import be.gamepath.projectgamepath.utility.MailManager;
import be.gamepath.projectgamepath.utility.Utility;
import be.gamepath.projectgamepath.utility.mail.Mail;
import be.gamepath.projectgamepath.utility.mail.MailSender;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class BasketBean extends CrudManaging<Basket> implements Serializable {

    //set name table for permission concatenation.
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

        EntityManager em = EMF.createEM();
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

    /**
     * submit a basket (insert order, delete basket, make PDF, send mail).
     * @return string redirection if work.
     */
/*// ---> submitBasket backup (work).
    public String submitBasket(){

        if(!connectionBean.isUserHasPermission(Crud.CREATE.getTxtValue() + "-order")){
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.titleErrorNoPermission"),
                    Utility.stringFromI18N("application.crudPage.messageErrorNoPermission"),
                    false
            );
            return null;
        }

        EntityManager em = EMF.createEM();
        OrderService orderService = new OrderService();
        ProductKeyService productKeyService = new ProductKeyService();
        BasketProductTheoricService basketProductTheoricService = new BasketProductTheoricService();
        BasketService basketService = new BasketService();
        UserProductTheoricService userProductTheoricService = new UserProductTheoricService();
        EntityTransaction transaction = EMF.getTransaction(em);
        boolean isSuccess = false;
        Order order = new Order();

        try{
            transaction.begin();

            //make order.
            order.setUser(this.elementCrudSelected.getUser());
            order.setPayementType(this.elementCrudSelected.getPayementType());
            order.setValidateBasketDate(Utility.castLocalDateTimeToDate(LocalDateTime.now()));
            order.setIsForMe(this.elementCrudSelected.getIsForMe());

            //insert order.
            order = orderService.insert(em, order);
            EMF.transactionCommit(em, transaction); //flush and commit the order for have the PK.

            //loop on every product in basket for add to order.
            ProductKey currentProducKey;
            order.setListProductKey(new ArrayList<>());
            for(ProductTheoric product : this.elementCrudSelected.getListProductTheoric()){

                transaction = EMF.getTransaction(em); //do one transaction by productKey in order.
                transaction.begin();

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
                EMF.transactionCommit(em, transaction); //flush and commit productKey for have the PK.

                //push in list transient.
                order.getListProductKey().add(currentProducKey);

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

                    transaction = EMF.getTransaction(em); //transaction for insert join library.
                    transaction.begin();

                    //create userProduct.
                    userProductTheoric = new UserProductTheoric();
                    userProductTheoric.setUser(connectionBean.getUser());
                    userProductTheoric.setProductTheoric(currentProducKey.getProductTheoric());
                    userProductTheoric.setKeyUsed(currentProducKey.getKey());
                    userProductTheoric.setUnlockDate(Utility.castLocalDateTimeToDate(LocalDateTime.now()));
                    userProductTheoricService.insert(em, userProductTheoric);

                    EMF.transactionCommit(em, transaction);
                }

                transaction = EMF.getTransaction(em); //transaction for update productKey with key generate.
                transaction.begin();

                //update productKey (for key generated).
                productKeyService.update(em, currentProducKey);

                EMF.transactionCommit(em, transaction);
            }

            //if(order.getId() != 0)
            //    throw new Exception("fake error");

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

            //if order make successfully, delete basket and his joins.
            transaction = EMF.getTransaction(em);
            transaction.begin();

            for(ProductTheoric product : this.elementCrudSelected.getListProductTheoric()){
                basketProductTheoricService.delete(em,
                        basketProductTheoricService.selectByBothId(em,
                                this.elementCrudSelected.getId(), product.getId()));
            }
            basketService.delete(em, this.elementCrudSelected);
            this.elementCrudSelected = null;

            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.basket.titleSuccessSubmit"),
                    Utility.stringFromI18N("application.basket.messageSuccessSubmit"),
                    true
            );

            isSuccess = true;
            EMF.transactionCommit(em, transaction);
        }catch(Exception e){
            if(transaction.isActive()) //can be not active.
                EMF.transactionRollback(em, transaction);

            if(order.getId() != 0){ //need to cancel insert order (and eventually join productKey).

                try{
                    transaction = EMF.getTransaction(em);
                    transaction.begin();

                    //foreach delete productKey (and join library).
                    for(ProductKey currentProducKey : order.getListProductKey()){
                        if(this.elementCrudSelected.getIsForMe()){ //delete join library.
                            UserProductTheoric userProductTheoric = userProductTheoricService.selectByBothId(em,
                                    connectionBean.getUser().getId(), currentProducKey.getProductTheoric().getId());
                            userProductTheoricService.delete(em, userProductTheoric);
                        }
                        productKeyService.delete(em, currentProducKey); //delete productKey.
                    }

                    //delete order.
                    orderService.delete(em, order);

                    EMF.transactionCommit(em, transaction);
                }catch(Exception exceptionDeleteFullOrder){
                    EMF.transactionRollback(em, transaction);
                    Utility.debug("error into submitBasket (exceptionDeleteFullOrder) : " + exceptionDeleteFullOrder.getMessage());
                    Utility.debug("order id " + (order.getId()) + " need to be deleted properly (order, productKey, userProduct) !");
                }

            }

            Utility.debug("error into submitBasket : " + e.getMessage());
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.basket.titleErrorSubmit"),
                    Utility.stringFromI18N("application.basket.messageErrorSubmit"),
                    false
            );
            isSuccess = false;
        }finally{
            if(transaction.isActive()) //last security.
                EMF.transactionRollback(em, transaction);
            em.close();
        }

        if(!isSuccess)
            return null;
        //if(this.elementCrudSelected.getIsForMe()) //idee, for redirect to my library if order for me.
        //    return "";
        return connectionBean.getPathHomePage();
    }
//*/

//*// ---> submitBasket. (work in progress)
    public String submitBasket(){

        if(!connectionBean.isUserHasPermission(Crud.CREATE.getTxtValue() + "-order")){
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.titleErrorNoPermission"),
                    Utility.stringFromI18N("application.crudPage.messageErrorNoPermission"),
                    false
            );
            return null;
        }

        EntityManager em = EMF.createEM();
        em.setFlushMode(FlushModeType.COMMIT); //set flush mode in commit.
        OrderService orderService = new OrderService();
        ProductKeyService productKeyService = new ProductKeyService();
        BasketProductTheoricService basketProductTheoricService = new BasketProductTheoricService();
        BasketService basketService = new BasketService();
        UserProductTheoricService userProductTheoricService = new UserProductTheoricService();
        EntityTransaction transaction = EMF.getTransaction(em);
        boolean isSuccess = false;
        Order order = new Order();

        try{
            transaction.begin();

            //make order.
            order.setUser(this.elementCrudSelected.getUser());
            order.setPayementType(this.elementCrudSelected.getPayementType());
            order.setValidateBasketDate(Utility.castLocalDateTimeToDate(LocalDateTime.now()));
            order.setIsForMe(this.elementCrudSelected.getIsForMe());

            //insert order.
            order = orderService.insert(em, order);
            em.flush(); //flush the order for have the PK.

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
                em.flush(); //flush productKey for have the PK.

                //push in list transient.
                order.getListProductKey().add(currentProducKey);

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

                    em.flush();
                }

                //update productKey (for key generated).
                productKeyService.update(em, currentProducKey);

                em.flush();
            }

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

            //email olf version (for test).
            /*
            try {
                Mail email = new Mail();
                email.setFrom("projectgamepath@outlook.com");
                email.setMsgBody("Votre commande à bien été passée ! \n\n" +
                    "Vous trouverez ci-dessous les détailles de votre commande. \n\n" +
                    "L'équipe de GamePath vous remercie.");
                email.setSubject("Votre commande");
                List<String> listEMail = new ArrayList<String>();
                email.setListTo(listEMail);
                email.getListTo().add(order.getUser().getEmail());
                email.setReplyTo("projectgamepath@outlook.com");
                email.setNick("GamePath");
                email.setEncodeUTF8(true);

                MailSender.sendMail(email);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            for(ProductTheoric product : this.elementCrudSelected.getListProductTheoric()){
                basketProductTheoricService.delete(em,
                        basketProductTheoricService.selectByBothId(em,
                                this.elementCrudSelected.getId(), product.getId()));
            }
            basketService.delete(em, this.elementCrudSelected);
            this.elementCrudSelected = null;

            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.basket.titleSuccessSubmit"),
                    Utility.stringFromI18N("application.basket.messageSuccessSubmit"),
                    true
            );

            isSuccess = true;
            EMF.transactionCommit(em, transaction);
        }catch(Exception e){
            if(transaction.isActive()) //can be not active.
                EMF.transactionRollback(em, transaction);

            if(order.getId() != 0){ //need to cancel insert order (and eventually join productKey).

                try{
                    transaction = EMF.getTransaction(em);
                    transaction.begin();

                    //foreach delete productKey (and join library).
                    for(ProductKey currentProducKey : order.getListProductKey()){
                        if(this.elementCrudSelected.getIsForMe()){ //delete join library.
                            UserProductTheoric userProductTheoric = userProductTheoricService.selectByBothId(em,
                                    connectionBean.getUser().getId(), currentProducKey.getProductTheoric().getId());
                            userProductTheoricService.delete(em, userProductTheoric);
                        }
                        productKeyService.delete(em, currentProducKey); //delete productKey.
                    }

                    //delete order.
                    orderService.delete(em, order);

                    EMF.transactionCommit(em, transaction);
                }catch(Exception exceptionDeleteFullOrder){
                    EMF.transactionRollback(em, transaction);
                    Utility.debug("error into submitBasket (exceptionDeleteFullOrder) : " + exceptionDeleteFullOrder.getMessage());
                    Utility.debug("order id " + (order.getId()) + " need to be deleted properly (order, productKey, userProduct) !");
                }

            }

            Utility.debug("error into submitBasket : " + e.getMessage());
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.basket.titleErrorSubmit"),
                    Utility.stringFromI18N("application.basket.messageErrorSubmit"),
                    false
            );
            isSuccess = false;
        }finally{
            if(transaction.isActive()) //last security.
                EMF.transactionRollback(em, transaction);
            em.close();
        }

        if(!isSuccess)
            return null;
        //if(this.elementCrudSelected.getIsForMe()) //idee, for redirect to my library if order for me.
        //    return "";
        return connectionBean.getPathHomePage();
    }
//*/

    /**
     * Load basket from DB (if is a fake basket empty)
     */
    public void loadBasketFromDB(){

        if(this.getElementCrudSelected().getId() != 0 && this.getElementCrudSelected().getListProductTheoric().size() != 0)
            return; //basket is already load.

        EntityManager em = EMF.createEM();
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
