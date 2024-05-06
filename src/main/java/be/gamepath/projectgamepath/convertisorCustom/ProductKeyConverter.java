package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.ProductKey;
import be.gamepath.projectgamepath.service.ProductKeyService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("ProductKeyConverter")
public class ProductKeyConverter extends ConverterGeneric<ProductKey, ProductKeyService> {

    //send service to parent.
    public ProductKeyConverter(){
        this.service = new ProductKeyService();
    }

}
