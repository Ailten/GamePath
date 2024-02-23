package com.example.gamepath.convertisorCustom;

import com.example.gamepath.connexion.EMF;
import com.example.gamepath.entities.PegiEntity;
import com.example.gamepath.service.PegiService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("PegiConverter")
public class PegiConverter implements Converter {

    //cast from string to object.
    @Override
    public PegiEntity getAsObject(FacesContext context, UIComponent component, String value)
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
    public static PegiEntity getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        PegiService pegiService = new PegiService();
        PegiEntity pegi;
        try{
            pegi = pegiService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            pegi = null;
        }finally{
            em.close();
        }
        return pegi;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        PegiEntity pegi = (PegiEntity) value;
        return String.valueOf(pegi.getIdPegi());
    }

}