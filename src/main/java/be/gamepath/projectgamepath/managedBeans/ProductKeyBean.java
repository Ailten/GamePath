package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.ProductKey;
import be.gamepath.projectgamepath.entities.UserProductTheoric;
import be.gamepath.projectgamepath.service.ProductKeyService;
import be.gamepath.projectgamepath.service.UserProductTheoricService;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.time.LocalDateTime;

@Named
@SessionScoped
public class ProductKeyBean implements Serializable {

    @Inject
    ConnectionBean connectionBean;
    @Inject
    PopUpMessageBean popUpMessageBean;

    /**
     * function for use a key (make ProductKey used and insert UserProduct).
     * @param productKey the productKey to use.
     */
    public void useKey(ProductKey productKey){

        if(!connectionBean.isUserHasPermission("create-userproduct")){
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.titleErrorNoPermission"),
                    Utility.stringFromI18N("application.crudPage.messageErrorNoPermission"),
                    false
            );
            return;
        }

        EntityManager em = EMF.createEM();
        UserProductTheoricService userProductTheoricService = new UserProductTheoricService();
        ProductKeyService productKeyService = new ProductKeyService();
        EntityTransaction transaction = EMF.getTransaction(em);

        try{
            transaction.begin();

            if(!productKey.getIsValid()){
                popUpMessageBean.setPopUpMessage(
                        Utility.stringFromI18N("application.crudUserProductTheoric.errorTitleKeyAlreadyUsed"),
                        Utility.stringFromI18N("application.crudUserProductTheoric.errorMessageKeyAlreadyUsed"),
                        false
                );
                throw new Exception("error, the key was already used.");
            }

            UserProductTheoric userProduct = userProductTheoricService.selectByBothId(em,
                    connectionBean.getUser().getId(), productKey.getProductTheoric().getId());
            if(userProduct != null){
                popUpMessageBean.setPopUpMessage(
                        Utility.stringFromI18N("application.crudUserProductTheoric.errorTitleAlreadyHaveProduct"),
                        Utility.stringFromI18N("application.crudUserProductTheoric.errorMessageAlreadyHaveProduct"),
                        false
                );
                throw new Exception("error, user already has this product.");
            }

            //update the productKey (as used).
            productKey.setIsValid(false);
            productKeyService.update(em, productKey);

            //create userProduct.
            userProduct = new UserProductTheoric();
            userProduct.setUser(connectionBean.getUser());
            userProduct.setProductTheoric(productKey.getProductTheoric());
            userProduct.setKeyUsed(productKey.getKey());
            userProduct.setUnlockDate(Utility.castLocalDateTimeToDate(LocalDateTime.now()));
            userProductTheoricService.insert(em, userProduct);

            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudUserProductTheoric.successTitle"),
                    Utility.stringFromI18N("application.crudUserProductTheoric.successMessage"),
                    true
            );

            EMF.transactionCommit(em, transaction);
        }catch(Exception e){
            EMF.transactionRollback(em, transaction);
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudUserProductTheoric.errorTitle"),
                    Utility.stringFromI18N("application.crudUserProductTheoric.errorMessage"),
                    false
            );
            Utility.debug("error into useKey : " + e.getMessage());
        }finally{
            if(transaction.isActive()) //last security.
                EMF.transactionRollback(em, transaction);
            em.close();
        }

    }

}
