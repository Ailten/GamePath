package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.Order;
import be.gamepath.projectgamepath.service.OrderService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("OrderConverter")
public class OrderConverter extends ConverterGeneric<Order, OrderService> {

    public OrderConverter(){
        this.service = new OrderService();
    }

    /*
    //cast from string to object.
    @Override
    public Order getAsObject(FacesContext context, UIComponent component, String value)
    {
        return getAsObjectStatic(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        return getAsStringStatic(value);
    }

    //static cast from string to object.
    public static Order getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        OrderService orderService = new OrderService();
        Order order;
        try{
            order = orderService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            order = null;
        }finally{
            em.close();
        }
        return order;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        Order order = (Order) value;
        return String.valueOf(order.getId());
    }
    */
}
