package be.gamepath.projectgamepath.managedBeans;


import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.ProductTheoric;
import be.gamepath.projectgamepath.enumeration.Crud;
import be.gamepath.projectgamepath.service.ProductTheoricService;
import be.gamepath.projectgamepath.utility.CrudManaging;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@Named
@SessionScoped
public class ProductTheoricBean extends CrudManaging<ProductTheoric> implements Serializable {

    public ProductTheoricBean(){
        this.nameEntityForPermission = "product"; //send name of entity for check permission generic.
    }

    /**
     * Get entity select for crud page (if null and create mode, instance a new entity).
     */
    public ProductTheoric getElementCrudSelected(){
        if(elementCrudSelected == null && modeSelected == Crud.CREATE)
            elementCrudSelected = new ProductTheoric();
        return elementCrudSelected;
    }

    protected boolean insert() {

        EntityManager em = EMF.getEM();
        ProductTheoricService productTheoricService = new ProductTheoricService();
        EntityTransaction transaction = em.getTransaction();
        boolean success;

        try{
            transaction.begin();

            //try get an element from DB with same title, if found : error.
            if(productTheoricService.selectByTitle(em, this.elementCrudSelected.getTitle()) != null){
                popUpMessageBean.setPopUpMessage(
                        Utility.stringFromI18N("application.crudPage.titleSuccess"),
                        Utility.stringFromI18N("application.crudProductTheoric.errorMessageTitleDuplicate"),
                        false
                );
                throw new Exception("error champ duplicate");
            }

            //insert element into DB.
            productTheoricService.insert(em, this.elementCrudSelected);

            success = true;
            transaction.commit();
        }catch(Exception e){
            success = false;
        }finally{
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }

        return success;
    }

    protected boolean update() {

        EntityManager em = EMF.getEM();
        ProductTheoricService productTheoricService = new ProductTheoricService();
        EntityTransaction transaction = em.getTransaction();
        boolean success;

        try{
            transaction.begin();

            //try get an element from DB with same title, if found : error.
            if(productTheoricService.selectByTitle(em, this.elementCrudSelected.getTitle()) != null){
                popUpMessageBean.setPopUpMessage(
                        Utility.stringFromI18N("application.crudPage.titleSuccess"),
                        Utility.stringFromI18N("application.crudProductTheoric.errorMessageTitleDuplicate"),
                        false
                );
                throw new Exception("error champ duplicate");
            }

            //insert element into DB.
            productTheoricService.update(em, this.elementCrudSelected);

            success = true;
            transaction.commit();
        }catch(Exception e){
            success = false;
        }finally{
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }

        return success;
    }



}
