package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.PictureProduct;
import be.gamepath.projectgamepath.service.PictureProductService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("PictureProductConverter")
public class PictureProductConverter extends ConverterGeneric<PictureProduct, PictureProductService> {

    //send service to parent.
    public PictureProductConverter(){
        this.service = new PictureProductService();
    }

}
