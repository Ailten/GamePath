package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.Basket;
import be.gamepath.projectgamepath.service.BasketService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("BasketConverter")
public class BasketConverter extends ConverterGeneric<Basket, BasketService> {

    //send service to parent.
    public BasketConverter(){
        this.service = new BasketService();
    }

}
