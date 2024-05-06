package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.Order;
import be.gamepath.projectgamepath.service.OrderService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("OrderConverter")
public class OrderConverter extends ConverterGeneric<Order, OrderService> {

    //send service to parent.
    public OrderConverter(){
        this.service = new OrderService();
    }

}
