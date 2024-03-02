package be.gamepath.projectgamepath.managedBeans;


import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.*;
import be.gamepath.projectgamepath.enumeration.Crud;
import be.gamepath.projectgamepath.service.*;
import be.gamepath.projectgamepath.utility.CrudManaging;
import be.gamepath.projectgamepath.utility.FileManaging;
import be.gamepath.projectgamepath.utility.Utility;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            this.elementCrudSelected = productTheoricService.insert(em, this.elementCrudSelected);

            //insert/delete joins.
            this.updateJoins(em);

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
            ProductTheoric productGetByTitle = productTheoricService.selectByTitle(em, this.elementCrudSelected.getTitle());
            if(productGetByTitle != null && productGetByTitle.getId() != this.elementCrudSelected.getId()){
                popUpMessageBean.setPopUpMessage(
                        Utility.stringFromI18N("application.crudPage.titleSuccess"),
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

    public void delete(ProductTheoric productTheoric) {

        this.elementCrudSelected = productTheoric;

        EntityManager em = EMF.getEM();
        ProductTheoricService productTheoricService = new ProductTheoricService();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();

            //insert/delete joins.
            this.updateJoins(em);

            //delete element into DB.
            productTheoricService.delete(em, this.elementCrudSelected);

            transaction.commit();
        }catch(Exception e){
            Utility.debug("error into delete : " + e.getMessage());
        }finally{
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }

    }

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
        EntityManager em = EMF.getEM();
        CategoryService categoryService = new CategoryService();

        try
        {
            productTheoric.setListCategory(
                    categoryService.selectManyByIdProductTheoric(em, productTheoric.getId())
            );
        }
        catch(Exception e)
        {
            Utility.debug("error into initListCategory : " + e.getMessage());
            productTheoric.setListCategory(new ArrayList<>());
        }
        finally
        {
            em.close();
        }
    }


    /**
     * Initialize list pegi into a productTheoric
     */
    public static void initListPegi(ProductTheoric productTheoric) {
        EntityManager em = EMF.getEM();
        PegiService pegiService = new PegiService();

        try
        {
            productTheoric.setListPegi(
                    pegiService.selectManyByIdProductTheoric(em, productTheoric.getId())
            );
        }
        catch(Exception e)
        {
            Utility.debug("error into initListPegi : " + e.getMessage());
            productTheoric.setListPegi(new ArrayList<>());
        }
        finally
        {
            em.close();
        }
    }


    /**
     * Initialize list operatingSystem into a productTheoric
     */
    public static void initListOperatingSystem(ProductTheoric productTheoric) {
        EntityManager em = EMF.getEM();
        OperatingSystemService operatingSystemService = new OperatingSystemService();

        try
        {
            productTheoric.setListOperatingSystem(
                    operatingSystemService.selectManyByIdProductTheoric(em, productTheoric.getId())
            );
        }
        catch(Exception e)
        {
            Utility.debug("error into initListOperatingSystem : " + e.getMessage());
            productTheoric.setListOperatingSystem(new ArrayList<>());
        }
        finally
        {
            em.close();
        }
    }


    /**
     * Initialize list language into a productTheoric
     */
    public static void initListLanguage(ProductTheoric productTheoric) {
        EntityManager em = EMF.getEM();
        LanguageService languageService = new LanguageService();

        try
        {
            productTheoric.setListLanguage(
                    languageService.selectManyByIdProductTheoric(em, productTheoric.getId())
            );
        }
        catch(Exception e)
        {
            Utility.debug("error into initListLanguage : " + e.getMessage());
            productTheoric.setListLanguage(new ArrayList<>());
        }
        finally
        {
            em.close();
        }
    }


    //for image file.
    private UploadedFile imageFile;
    public UploadedFile getImageFile(){ return this.imageFile; }
    public void setImageFile(UploadedFile imageFile){ this.imageFile = imageFile; }
    //public boolean imageFileIsNull(){
    //    return (imageFile == null && (this.elementCrudSelected.getPicture() == null || this.elementCrudSelected.getPicture().equals("")));
    //}
    public void fileUploadListener(FileUploadEvent event) throws IOException {

        EntityManager em = EMF.getEM();
        PictureProductService pictureProductService = new PictureProductService();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();

            this.imageFile = event.getFile(); //get file from event.

            //save file and set picture in entity.
            if(FileManaging.saveNewFile(this.imageFile, "pictureProduct")){ //save new file.
                //this.elementCrudSelected.setPicture(this.imageFile.getFileName()); //save in entity the name of image.

                //make entity pictureProduct.
                PictureProduct pictureProduct = new PictureProduct();
                if(this.elementCrudSelected.getId() == 0){
                    popUpMessageBean.setPopUpMessage(
                            Utility.stringFromI18N("application.crudProductTheoric.errorInputFileImageProductEmpty"),
                            Utility.stringFromI18N("application.crudProductTheoric.errorInputFileImageProductEmpty"),
                            false
                    );
                    throw new Exception("ProductTheoric has no Id (not create in DB), can't assign Id 0 has FK PictureProduct");
                }
                pictureProduct.setProductTheoric(this.elementCrudSelected);
                pictureProduct.setUrlImage(this.imageFile.getFileName());

                //--- error catch upper.

                //insert image in DB.
                pictureProduct = pictureProductService.insert(em, pictureProduct);

                //add image push into list in product transient (import to check before, because list can be select when call).
                if(!this.elementCrudSelected.getListPictureProduct().contains(pictureProduct)){
                    this.elementCrudSelected.getListPictureProduct().add(pictureProduct);
                }

            }

            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudProductTheoric.successInputFileImage"),
                    Utility.stringFromI18N("application.crudProductTheoric.successInputFileImage"),
                    true
            );

            transaction.commit();
        }catch(Exception e){
            Utility.debug("error into fileUploadListener : " + e.getMessage());
        }finally{
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }

    }


    /**
     * Initialize list pictureProduct into a productTheoric
     */
    public static void initListPictureProduct(ProductTheoric productTheoric) {

        EntityManager em = EMF.getEM();
        PictureProductService pictureProductService = new PictureProductService();

        try
        {
            productTheoric.setListPictureProduct(
                    pictureProductService.selectManyByIdProductTheoric(em, productTheoric.getId())
            );
        }
        catch(Exception e)
        {
            Utility.debug("error into initListPictureProduct : " + e.getMessage());
            productTheoric.setListPictureProduct(new ArrayList<>());
        }
        finally
        {
            em.close();
        }
    }



    public void deletePictureProduct(PictureProduct pictureProduct) {

        EntityManager em = EMF.getEM();
        PictureProductService pictureProductService = new PictureProductService();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();

            //remember id of picture after delete.
            int idPictureToDelete = pictureProduct.getId();

            //delete pictureProduct.
            pictureProductService.delete(em, pictureProduct);

            //remove picture from listPicture transient.
            this.elementCrudSelected.setListPictureProduct(
                    this.elementCrudSelected.getListPictureProduct().stream()
                            .filter(pp -> pp.getId() != idPictureToDelete).collect(Collectors.toList())
            );

            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.successTitleDelete"),
                    Utility.stringFromI18N("application.crudPage.successDelete"),
                    true
            );

            transaction.commit();
        }catch(Exception e){
            Utility.debug("error into deletePictureProduct : " + e.getMessage());
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.errorTitleDelete"),
                    Utility.stringFromI18N("application.crudPage.errorDelete"),
                    false
            );
        }finally{
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }

    }

}
