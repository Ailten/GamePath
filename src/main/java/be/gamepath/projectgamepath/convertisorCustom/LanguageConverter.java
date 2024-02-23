package com.example.gamepath.convertisorCustom;

import com.example.gamepath.connexion.EMF;
import com.example.gamepath.entities.LanguageEntity;
import com.example.gamepath.service.LanguageService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("LanguageConverter")
public class LanguageConverter implements Converter {

    //cast from string to object.
    @Override
    public LanguageEntity getAsObject(FacesContext context, UIComponent component, String value)
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
    public static LanguageEntity getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        LanguageService languageService = new LanguageService();
        LanguageEntity language;
        try{
            language = languageService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            language = null;
        }finally{
            em.close();
        }
        return language;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        LanguageEntity language = (LanguageEntity) value;
        return String.valueOf(language.getIdLanguage());
    }

}
