package com.example.gamepath.convertisorCustom;

import com.example.gamepath.connexion.EMF;
import com.example.gamepath.entities.UserProductTheoricEntity;
import com.example.gamepath.service.UserProductTheoricService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("UserProductTheoricConverter")
public class UserProductTheoricConverter implements Converter {

    //cast from string to object.
    @Override
    public UserProductTheoricEntity getAsObject(FacesContext context, UIComponent component, String value)
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
    public static UserProductTheoricEntity getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        UserProductTheoricService userProductTheoricService = new UserProductTheoricService();
        UserProductTheoricEntity userProductTheoric;
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
        UserProductTheoricEntity userProductTheoric = (UserProductTheoricEntity) value;
        return String.valueOf(userProductTheoric.getIdUserProductTheoric());
    }

}
