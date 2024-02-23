package com.example.gamepath.convertisorCustom;

import com.example.gamepath.connexion.EMF;
import com.example.gamepath.entities.PictureProductEntity;
import com.example.gamepath.service.PictureProductService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("PictureProductConverter")
public class PictureProductConverter implements Converter {

    //cast from string to object.
    @Override
    public PictureProductEntity getAsObject(FacesContext context, UIComponent component, String value)
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
    public static PictureProductEntity getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        PictureProductService pictureProductService = new PictureProductService();
        PictureProductEntity pictureProduct;
        try{
            pictureProduct = pictureProductService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            pictureProduct = null;
        }finally{
            em.close();
        }
        return pictureProduct;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        PictureProductEntity pictureProduct = (PictureProductEntity) value;
        return String.valueOf(pictureProduct.getIdPictureProduct());
    }

}
