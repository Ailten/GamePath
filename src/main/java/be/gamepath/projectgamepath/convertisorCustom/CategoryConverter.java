package com.example.gamepath.convertisorCustom;

import com.example.gamepath.connexion.EMF;
import com.example.gamepath.entities.CategoryEntity;
import com.example.gamepath.service.CategoryService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("CategoryConverter")
public class CategoryConverter implements Converter {

    //cast from string to object.
    @Override
    public CategoryEntity getAsObject(FacesContext context, UIComponent component, String value)
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
    public static CategoryEntity getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        CategoryService categoryService = new CategoryService();
        CategoryEntity category;
        try{
            category = categoryService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            category = null;
        }finally{
            em.close();
        }
        return category;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        CategoryEntity category = (CategoryEntity) value;
        return String.valueOf(category.getIdCategory());
    }

}

