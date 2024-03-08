package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.PictureProduct;
import be.gamepath.projectgamepath.enumeration.Crud;
import be.gamepath.projectgamepath.service.PictureProductService;
import be.gamepath.projectgamepath.utility.CrudManaging;
import be.gamepath.projectgamepath.utility.FileManaging;
import be.gamepath.projectgamepath.utility.Utility;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.io.Serializable;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class PictureProductBean extends CrudManaging<PictureProduct> implements Serializable {

    @Inject
    ProductTheoricBean productTheoricBean;

    public PictureProductBean(){
        this.nameEntityForPermission = "picture"; //send name of entity for check permission generic.
    }



    //for image file.
    private UploadedFile imageFile;
    public UploadedFile getImageFile(){ return this.imageFile; }
    public void setImageFile(UploadedFile imageFile){ this.imageFile = imageFile; }
    public void fileUploadListener(FileUploadEvent event) throws IOException {

        if(!connectionBean.isUserHasPermission(Crud.CREATE.getTxtValue() + "-" + this.nameEntityForPermission)){
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.titleErrorNoPermission"),
                    Utility.stringFromI18N("application.crudPage.messageErrorNoPermission"),
                    false
            );
            return;
        }

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
                this.elementCrudSelected = new PictureProduct();
                if(productTheoricBean.getElementCrudSelected().getId() == 0){
                    popUpMessageBean.setPopUpMessage(
                            Utility.stringFromI18N("application.crudProductTheoric.errorInputFileImageProductEmpty"),
                            Utility.stringFromI18N("application.crudProductTheoric.errorInputFileImageProductEmpty"),
                            false
                    );
                    throw new Exception("ProductTheoric has no Id (not create in DB), can't assign Id 0 has FK PictureProduct");
                }
                this.elementCrudSelected.setProductTheoric(productTheoricBean.getElementCrudSelected());
                this.elementCrudSelected.setUrlImage(this.imageFile.getFileName());

                //--- error catch upper.

                //insert image in DB.
                this.elementCrudSelected = pictureProductService.insert(em, this.elementCrudSelected);

                //add image push into list in product transient (import to check before, because list can be select when call).
                if(!productTheoricBean.getElementCrudSelected().getListPictureProduct().contains(this.elementCrudSelected)){
                    productTheoricBean.getElementCrudSelected().getListPictureProduct().add(this.elementCrudSelected);
                }

            }

            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudProductTheoric.successInputFileImage"),
                    Utility.stringFromI18N("application.crudProductTheoric.successInputFileImage"),
                    true
            );

            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
            Utility.debug("error into fileUploadListener : " + e.getMessage());
        }finally{
            if(transaction.isActive()) //last security.
                transaction.rollback();
            em.close();
        }

    }



    public void deletePictureProduct(PictureProduct pictureProduct) {

        if(!connectionBean.isUserHasPermission(Crud.DELETE.getTxtValue() + "-" + this.nameEntityForPermission)){
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.titleErrorNoPermission"),
                    Utility.stringFromI18N("application.crudPage.messageErrorNoPermission"),
                    false
            );
            return;
        }

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
            productTheoricBean.getElementCrudSelected().setListPictureProduct(
                    productTheoricBean.getElementCrudSelected().getListPictureProduct().stream()
                            .filter(pp -> pp.getId() != idPictureToDelete).collect(Collectors.toList())
            );

            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.successTitleDelete"),
                    Utility.stringFromI18N("application.crudPage.successDelete"),
                    true
            );

            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
            Utility.debug("error into deletePictureProduct : " + e.getMessage());
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
