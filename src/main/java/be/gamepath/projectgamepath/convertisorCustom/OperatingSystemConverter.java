package com.example.gamepath.convertisorCustom;

import com.example.gamepath.connexion.EMF;
import com.example.gamepath.entities.OperatingSystemEntity;
import com.example.gamepath.service.OperatingSystemService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("OperatingSystemConverter")
public class OperatingSystemConverter implements Converter {

    //cast from string to object.
    @Override
    public OperatingSystemEntity getAsObject(FacesContext context, UIComponent component, String value)
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
    public static OperatingSystemEntity getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        OperatingSystemService operatingSystemService = new OperatingSystemService();
        OperatingSystemEntity operatingSystem;
        try{
            operatingSystem = operatingSystemService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            operatingSystem = null;
        }finally{
            em.close();
        }
        return operatingSystem;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        OperatingSystemEntity operatingSystem = (OperatingSystemEntity) value;
        return String.valueOf(operatingSystem.getIdOperatingSystem());
    }

}
