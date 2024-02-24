package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.UserProductTheoric;
import be.gamepath.projectgamepath.service.UserProductTheoricService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("UserProductTheoricConverter")
public class UserProductTheoricConverter implements Converter {

    //cast from string to object.
    @Override
    public UserProductTheoric getAsObject(FacesContext context, UIComponent component, String value)
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
    public static UserProductTheoric getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        UserProductTheoricService userProductTheoricService = new UserProductTheoricService();
        UserProductTheoric userProductTheoric;
        try{
            userProductTheoric = userProductTheoricService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            userProductTheoric = null;
        }finally{
            em.close();
        }
        return userProductTheoric;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        UserProductTheoric userProductTheoric = (UserProductTheoric) value;
        return String.valueOf(userProductTheoric.getId());
    }

}
