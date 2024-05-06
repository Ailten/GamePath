package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.ProductTheoric;
import be.gamepath.projectgamepath.service.ProductTheoricService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("ProductTheoricConverter")
public class ProductTheoricConverter extends ConverterGeneric<ProductTheoric, ProductTheoricService> {

    //send service to parent.
    public ProductTheoricConverter(){
        this.service = new ProductTheoricService();
    }

}
