package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.Pegi;
import be.gamepath.projectgamepath.service.PegiService;
import be.gamepath.projectgamepath.utility.ConvertisorGeneric;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("PegiConverter")
public class PegiConverter extends ConvertisorGeneric<Pegi, PegiService> {

    public PegiConverter(){
        this.service = new PegiService();
    }

    /*
    //cast from string to object.
    @Override
    public Pegi getAsObject(FacesContext context, UIComponent component, String value)
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
    public static Pegi getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        PegiService pegiService = new PegiService();
        Pegi pegi;
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
        Pegi pegi = (Pegi) value;
        return String.valueOf(pegi.getId());
    }
    */
}
