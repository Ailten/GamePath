package com.example.gamepath.convertisorCustom;

import com.example.gamepath.connexion.EMF;
import com.example.gamepath.entities.SocietyProducerEntity;
import com.example.gamepath.service.SocietyProducerService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("SocietyProducerConverter")
public class SocietyProducerConverter implements Converter {

    //cast from string to object.
    @Override
    public SocietyProducerEntity getAsObject(FacesContext context, UIComponent component, String value)
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
    public static SocietyProducerEntity getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        SocietyProducerService societyProducerService = new SocietyProducerService();
        SocietyProducerEntity societyProducer;
        try{
            societyProducer = societyProducerService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            societyProducer = null;
        }finally{
            em.close();
        }
        return societyProducer;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        SocietyProducerEntity societyProducer = (SocietyProducerEntity) value;
        return String.valueOf(societyProducer.getIdSocietyProducer());
    }

}
