package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.entities.Basket;
import be.gamepath.projectgamepath.entities.ProductTheoric;
import be.gamepath.projectgamepath.utility.CrudManaging;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class BasketBean extends CrudManaging<Basket> implements Serializable {

    public void addBasket(ProductTheoric productTheoric){

        //TODO: add product in basket client, and create basket if has not.

    }

}
