package com.example.gamepath.convertisorCustom;

import com.example.gamepath.connexion.EMF;
import com.example.gamepath.entities.UserEntity;
import com.example.gamepath.service.UserService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("UserConverter")
public class UserConverter implements Converter {

    //cast from string to object.
    @Override
    public UserEntity getAsObject(FacesContext context, UIComponent component, String value)
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
    public static UserEntity getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        UserService userService = new UserService();
        UserEntity user;
        try{
            user = userService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            user = null;
        }finally{
            em.close();
        }
        return user;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        UserEntity user = (UserEntity) value;
        return String.valueOf(user.getIdUser());
    }

}
