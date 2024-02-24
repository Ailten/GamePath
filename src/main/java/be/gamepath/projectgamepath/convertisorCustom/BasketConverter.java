package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.Basket;
import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.service.BasketService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("BasketConverter")
public class BasketConverter implements Converter {

    //cast from string to object.
    @Override
    public Basket getAsObject(FacesContext context, UIComponent component, String value)
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
    public static Basket getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        BasketService basketService = new BasketService();
        Basket basket;
        try{
            basket = basketService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            basket = null;
        }finally{
            em.close();
        }
        return basket;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        Basket basket = (Basket) value;
        return String.valueOf(basket.getId());
    }

}
