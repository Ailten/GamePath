package be.gamepath.projectgamepath.managedBeans;


import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.*;
import be.gamepath.projectgamepath.enumeration.Crud;
import be.gamepath.projectgamepath.service.*;
import be.gamepath.projectgamepath.utility.CrudManaging;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class ProductTheoricBean extends CrudManaging<ProductTheoric> implements Serializable {

    //set name table for permission concatenation.
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

    /**
     * override insert of parent (call in trySubmitCrudForm generic).
     * @return true if insert successfully.
     */
    protected boolean insert() {

        EntityManager em = EMF.createEM();
        ProductTheoricService productTheoricService = new ProductTheoricService();
        EntityTransaction transaction = EMF.getTransaction(em);
        boolean success;

        try{
            transaction.begin();

            //try get an element from DB with same title, if found : error.
            if(productTheoricService.selectByTitle(em, this.elementCrudSelected.getTitle()) != null){
                popUpMessageBean.setPopUpMessage(
                        Utility.stringFromI18N("application.crudPage.errorTitleDuplicate"),
                        Utility.stringFromI18N("application.crudProductTheoric.errorMessageTitleDuplicate"),
                        false
                );
                throw new Exception("error champ duplicate");
            }

            //insert element into DB.
            this.elementCrudSelected = productTheoricService.insert(em, this.elementCrudSelected);

            //insert/delete joins.
            this.updateJoins(em);

            success = true;
            EMF.transactionCommit(em, transaction);
        }catch(Exception e){
            EMF.transactionRollback(em, transaction);
            success = false;
        }finally{
            if(transaction.isActive()) //last security.
                EMF.transactionRollback(em, transaction);
            em.close();
        }

        return success;
    }

    /**
     * override update of parent (call in trySubmitCrudForm generic).
     * @return true if update successfully.
     */
    protected boolean update() {

        EntityManager em = EMF.createEM();
        ProductTheoricService productTheoricService = new ProductTheoricService();
        EntityTransaction transaction = EMF.getTransaction(em);
        boolean success;

        try{
            transaction.begin();

            //try get an element from DB with same title, if found : error.
            ProductTheoric productGetByTitle = productTheoricService.selectByTitle(em, this.elementCrudSelected.getTitle());
            if(productGetByTitle != null && productGetByTitle.getId() != this.elementCrudSelected.getId()){
                popUpMessageBean.setPopUpMessage(
                        Utility.stringFromI18N("application.crudPage.errorTitleDuplicate"),
                        Utility.stringFromI18N("application.crudProductTheoric.errorMessageTitleDuplicate"),
                        false
                );
                throw new Exception("error champ duplicate");
            }

            //insert element into DB.
            productTheoricService.update(em, this.elementCrudSelected);

            //insert/delete joins.
            this.updateJoins(em);

            success = true;
            EMF.transactionCommit(em, transaction);
        }catch(Exception e){
            EMF.transactionRollback(em, transaction);
            success = false;
        }finally{
            if(transaction.isActive()) //last security.
                EMF.transactionRollback(em, transaction);
            em.close();
        }

        return success;
    }

    /**
     * delete an element ProductTheoric.
     */
    public void delete(ProductTheoric productTheoric) {

        if(!connectionBean.isUserHasPermission(Crud.DELETE.getTxtValue() + "-" + nameEntityForPermission)){
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.titleErrorNoPermission"),
                    Utility.stringFromI18N("application.crudPage.messageErrorNoPermission"),
                    false
            );
            return;
        }

        this.elementCrudSelected = productTheoric;

        EntityManager em = EMF.createEM();
        ProductTheoricService productTheoricService = new ProductTheoricService();
        EntityTransaction transaction = EMF.getTransaction(em);

        try{
            transaction.begin();

            //insert/delete joins.
            this.updateJoins(em);

            //delete element into DB.
            productTheoricService.delete(em, this.elementCrudSelected);

            //clean var object selected.
            this.elementCrudSelected = null;

            EMF.transactionCommit(em, transaction);
        }catch(Exception e){
            EMF.transactionRollback(em, transaction);
            Utility.debug("error into delete : " + e.getMessage());
        }finally{
            if(transaction.isActive()) //last security.
                EMF.transactionRollback(em, transaction);
            em.close();
        }

    }

    /**
     * insert, update, delete all table joins of ProductTheoric.
     * @param em EntityManager.
     * @throws Exception can return an exception (need to be handle).
     */
    private void updateJoins(EntityManager em) throws Exception {

        //--- Category joins.

        CategoryService categoryService = new CategoryService();
        ProductTheoricCategoryService productTheoricCategoryService = new ProductTheoricCategoryService();

        List<Category> listCategoryFromDB = categoryService.selectManyByIdProductTheoric(em, this.elementCrudSelected.getId());

        //insert join table of ProductTheoricCategory.
        if(this.isModeSelected(Crud.CREATE, Crud.UPDATE)){
            List<Category> listCategoryToInsert = this.elementCrudSelected.getListCategory().stream()
                    .filter(c -> !listCategoryFromDB.contains(c)).collect(Collectors.toList());
            for(Category categoryToInsert : listCategoryToInsert) {
                ProductTheoricCategory ptcToInsert = new ProductTheoricCategory();
                ptcToInsert.setProductTheoric(this.elementCrudSelected);
                ptcToInsert.setCategory(categoryToInsert);
                productTheoricCategoryService.insert(em, ptcToInsert);
            }
        }

        //delete join table of ProductTheoricCategory.
        if(this.isModeSelected(Crud.UPDATE, Crud.DELETE)){
            List<Category> listCategoryToDelete = (this.isModeSelected(Crud.UPDATE)?
                    listCategoryFromDB.stream()
                            .filter(c -> !this.elementCrudSelected.getListCategory().contains(c)).collect(Collectors.toList()):
                    this.elementCrudSelected.getListCategory()
            );
            for(Category categoryToDelete : listCategoryToDelete) {
                ProductTheoricCategory ptcToDelete = productTheoricCategoryService.selectByBothId(em, this.elementCrudSelected.getId(), categoryToDelete.getId());
                if(ptcToDelete == null)
                    continue;
                productTheoricCategoryService.delete(em, ptcToDelete);
            }
        }

        //--- Pegi joins.

        PegiService pegiService = new PegiService();
        ProductTheoricPegiService productTheoricPegiService = new ProductTheoricPegiService();

        List<Pegi> listPegiFromDB = pegiService.selectManyByIdProductTheoric(em, this.elementCrudSelected.getId());

        //insert join table of ProductTheoricPegi.
        if(this.isModeSelected(Crud.CREATE, Crud.UPDATE)){
            List<Pegi> listPegiToInsert = this.elementCrudSelected.getListPegi().stream()
                    .filter(c -> !listPegiFromDB.contains(c)).collect(Collectors.toList());
            for(Pegi pegiToInsert : listPegiToInsert) {
                ProductTheoricPegi ptpToInsert = new ProductTheoricPegi();
                ptpToInsert.setProductTheoric(this.elementCrudSelected);
                ptpToInsert.setPegi(pegiToInsert);
                productTheoricPegiService.insert(em, ptpToInsert);
            }
        }

        //delete join table of ProductTheoricPegi.
        if(this.isModeSelected(Crud.UPDATE, Crud.DELETE)){
            List<Pegi> listPegiToDelete = (this.isModeSelected(Crud.UPDATE)?
                    listPegiFromDB.stream()
                            .filter(c -> !this.elementCrudSelected.getListPegi().contains(c)).collect(Collectors.toList()):
                    this.elementCrudSelected.getListPegi()
            );
            for(Pegi pegiToDelete : listPegiToDelete) {
                ProductTheoricPegi ptpToDelete = productTheoricPegiService.selectByBothId(em, this.elementCrudSelected.getId(), pegiToDelete.getId());
                if(ptpToDelete == null)
                    continue;
                productTheoricPegiService.delete(em, ptpToDelete);
            }
        }

        //--- OperatingSystem joins.

        OperatingSystemService operatingSystemService = new OperatingSystemService();
        ProductTheoricOperatingSystemService productTheoricOperatingSystemService = new ProductTheoricOperatingSystemService();

        List<OperatingSystem> listOperatingSystemFromDB = operatingSystemService.selectManyByIdProductTheoric(em, this.elementCrudSelected.getId());

        //insert join table of ProductTheoricOperatingSystem.
        if(this.isModeSelected(Crud.CREATE, Crud.UPDATE)){
            List<OperatingSystem> listOperatingSystemToInsert = this.elementCrudSelected.getListOperatingSystem().stream()
                    .filter(c -> !listOperatingSystemFromDB.contains(c)).collect(Collectors.toList());
            for(OperatingSystem operatingSystemToInsert : listOperatingSystemToInsert) {
                ProductTheoricOperatingSystem ptosToInsert = new ProductTheoricOperatingSystem();
                ptosToInsert.setProductTheoric(this.elementCrudSelected);
                ptosToInsert.setOperatingSystem(operatingSystemToInsert);
                productTheoricOperatingSystemService.insert(em, ptosToInsert);
            }
        }

        //delete join table of ProductTheoricOperatingSystem.
        if(this.isModeSelected(Crud.UPDATE, Crud.DELETE)){
            List<OperatingSystem> listOperatingSystemToDelete = (this.isModeSelected(Crud.UPDATE)?
                    listOperatingSystemFromDB.stream()
                            .filter(c -> !this.elementCrudSelected.getListOperatingSystem().contains(c)).collect(Collectors.toList()):
                    this.elementCrudSelected.getListOperatingSystem()
            );
            for(OperatingSystem operatingSystemToDelete : listOperatingSystemToDelete) {
                ProductTheoricOperatingSystem ptopToDelete = productTheoricOperatingSystemService.selectByBothId(em, this.elementCrudSelected.getId(), operatingSystemToDelete.getId());
                if(ptopToDelete == null)
                    continue;
                productTheoricOperatingSystemService.delete(em, ptopToDelete);
            }
        }

        //--- Language joins.

        LanguageService languageService = new LanguageService();
        ProductTheoricLanguageService productTheoricLanguageService = new ProductTheoricLanguageService();

        List<Language> listLanguageFromDB = languageService.selectManyByIdProductTheoric(em, this.elementCrudSelected.getId());

        //insert join table of ProductTheoricLanguage.
        if(this.isModeSelected(Crud.CREATE, Crud.UPDATE)){
            List<Language> listLanguageToInsert = this.elementCrudSelected.getListLanguage().stream()
                    .filter(c -> !listLanguageFromDB.contains(c)).collect(Collectors.toList());
            for(Language languageToInsert : listLanguageToInsert) {
                ProductTheoricLanguage ptlToInsert = new ProductTheoricLanguage();
                ptlToInsert.setProductTheoric(this.elementCrudSelected);
                ptlToInsert.setLanguage(languageToInsert);
                productTheoricLanguageService.insert(em, ptlToInsert);
            }
        }

        //delete join table of ProductTheoricLanguage.
        if(this.isModeSelected(Crud.UPDATE, Crud.DELETE)){
            List<Language> listLanguageToDelete = (this.isModeSelected(Crud.UPDATE)?
                    listLanguageFromDB.stream()
                            .filter(c -> !this.elementCrudSelected.getListLanguage().contains(c)).collect(Collectors.toList()):
                    this.elementCrudSelected.getListLanguage()
            );
            for(Language languageToDelete : listLanguageToDelete) {
                ProductTheoricLanguage ptlToDelete = productTheoricLanguageService.selectByBothId(em, this.elementCrudSelected.getId(), languageToDelete.getId());
                if(ptlToDelete == null)
                    continue;
                productTheoricLanguageService.delete(em, ptlToDelete);
            }
        }

        //--- delete PictureProduct joins.

        if(this.isModeSelected(Crud.DELETE)){
            PictureProductService pictureProductService = new PictureProductService();
            List<PictureProduct> listPictureProductToDelete = this.elementCrudSelected.getListPictureProduct();
            for(PictureProduct pictureProductToDelete : listPictureProductToDelete) {
                pictureProductService.delete(em, pictureProductToDelete);
            }
        }

    }


    /**
     * Initialize list category into a productTheoric
     */
    public static void initListCategory(ProductTheoric productTheoric) {
        EntityManager em = EMF.createEM();
        CategoryService categoryService = new CategoryService();

        try{
            productTheoric.setListCategory(
                    categoryService.selectManyByIdProductTheoric(em, productTheoric.getId())
            );
        }catch(Exception e){
            Utility.debug("error into initListCategory : " + e.getMessage());
            productTheoric.setListCategory(new ArrayList<>());
        }finally{
            em.close();
        }
    }


    /**
     * Initialize list pegi into a productTheoric
     */
    public static void initListPegi(ProductTheoric productTheoric) {
        EntityManager em = EMF.createEM();
        PegiService pegiService = new PegiService();

        try{
            productTheoric.setListPegi(
                    pegiService.selectManyByIdProductTheoric(em, productTheoric.getId())
            );
        }catch(Exception e){
            Utility.debug("error into initListPegi : " + e.getMessage());
            productTheoric.setListPegi(new ArrayList<>());
        }finally{
            em.close();
        }
    }


    /**
     * Initialize list operatingSystem into a productTheoric
     */
    public static void initListOperatingSystem(ProductTheoric productTheoric) {
        EntityManager em = EMF.createEM();
        OperatingSystemService operatingSystemService = new OperatingSystemService();

        try{
            productTheoric.setListOperatingSystem(
                    operatingSystemService.selectManyByIdProductTheoric(em, productTheoric.getId())
            );
        }catch(Exception e){
            Utility.debug("error into initListOperatingSystem : " + e.getMessage());
            productTheoric.setListOperatingSystem(new ArrayList<>());
        }finally{
            em.close();
        }
    }


    /**
     * Initialize list language into a productTheoric
     */
    public static void initListLanguage(ProductTheoric productTheoric) {
        EntityManager em = EMF.createEM();
        LanguageService languageService = new LanguageService();

        try{
            productTheoric.setListLanguage(
                    languageService.selectManyByIdProductTheoric(em, productTheoric.getId())
            );
        }catch(Exception e){
            Utility.debug("error into initListLanguage : " + e.getMessage());
            productTheoric.setListLanguage(new ArrayList<>());
        }finally{
            em.close();
        }
    }


    /**
     * Initialize list pictureProduct into a productTheoric
     */
    public static void initListPictureProduct(ProductTheoric productTheoric) {

        EntityManager em = EMF.createEM();
        PictureProductService pictureProductService = new PictureProductService();

        try{
            productTheoric.setListPictureProduct(
                    pictureProductService.selectManyByIdProductTheoric(em, productTheoric.getId())
            );
        }catch(Exception e){
            Utility.debug("error into initListPictureProduct : " + e.getMessage());
            productTheoric.setListPictureProduct(new ArrayList<>());
        }finally{
            em.close();
        }
    }

}
