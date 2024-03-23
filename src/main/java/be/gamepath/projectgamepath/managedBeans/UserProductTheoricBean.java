package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.ProductKey;
import be.gamepath.projectgamepath.entities.UserProductTheoric;
import be.gamepath.projectgamepath.enumeration.Crud;
import be.gamepath.projectgamepath.service.ProductKeyService;
import be.gamepath.projectgamepath.service.UserProductTheoricService;
import be.gamepath.projectgamepath.utility.CrudManaging;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.time.LocalDateTime;

@Named
@SessionScoped
public class UserProductTheoricBean extends CrudManaging<UserProductTheoric> implements Serializable {

    public UserProductTheoricBean(){
        this.nameEntityForPermission = "userproduct"; //send name of entity for check permission generic.
    }

    /**
     * Get entity select for crud page (if null and create mode, instance a new entity).
     */
    public UserProductTheoric getElementCrudSelected(){
        if(elementCrudSelected == null && modeSelected == Crud.CREATE)
            elementCrudSelected = new UserProductTheoric();
        return elementCrudSelected;
    }

    protected boolean insert() {

        EntityManager em = EMF.createEM();
        ProductKeyService productKeyService = new ProductKeyService();
        UserProductTheoricService userProductTheoricService = new UserProductTheoricService();
        EntityTransaction transaction = EMF.getTransaction(em);
        boolean isSuccess = false;

        try{
            transaction.begin();

            ProductKey productKey = productKeyService.selectByKeyCode(em, this.elementCrudSelected.getKeyUsed());
            if(productKey == null){
                popUpMessageBean.setPopUpMessage(
                        Utility.stringFromI18N("application.crudUserProductTheoric.errorTitleKeyNotFound"),
                        Utility.stringFromI18N("application.crudUserProductTheoric.errorMessageKeyNotFound"),
                        false
                );
                throw new Exception("error, no productKey found.");
            }
            if(!productKey.getIsValid()){
                popUpMessageBean.setPopUpMessage(
                        Utility.stringFromI18N("application.crudUserProductTheoric.errorTitleKeyAlreadyUsed"),
                        Utility.stringFromI18N("application.crudUserProductTheoric.errorMessageKeyAlreadyUsed"),
                        false
                );
                throw new Exception("error, the key was already used.");
            }

            UserProductTheoric userProductAlready = userProductTheoricService.selectByBothId(em,
                    connectionBean.getUser().getId(), productKey.getProductTheoric().getId());
            if(userProductAlready != null){
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
            this.elementCrudSelected.setUser(connectionBean.getUser());
            this.elementCrudSelected.setProductTheoric(productKey.getProductTheoric());
            this.elementCrudSelected.setUnlockDate(Utility.castLocalDateTimeToDate(LocalDateTime.now()));
            userProductTheoricService.insert(em, this.elementCrudSelected);

            isSuccess = true;
            EMF.transactionCommit(em, transaction);
        }catch(Exception e){
            EMF.transactionRollback(em, transaction);
            Utility.debug("Error catch in insert : " + e.getMessage());
            isSuccess = false;
        }finally{
            if(transaction.isActive()) //last security.
                EMF.transactionRollback(em, transaction);
            em.close();
        }

        return isSuccess;
    }

}
