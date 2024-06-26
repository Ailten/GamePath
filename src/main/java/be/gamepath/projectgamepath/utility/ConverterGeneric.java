package be.gamepath.projectgamepath.utility;

import be.gamepath.projectgamepath.connexion.EMF;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;

public class ConverterGeneric<TEntity extends EntityGenerique, TService extends ServiceGeneric<TEntity>> implements Converter<TEntity> {

    protected TService service;

    //generique override of function getAsObject.
    @Override
    public TEntity getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s==null || s.equals("0") || s.equals("")) {
            return null;
        }

        EntityManager em = EMF.createEM();
        TEntity entity;
        try{
            entity = this.service.selectById(em, Integer.parseInt(s));
        }catch(Exception e){
            entity = null;
        }finally{
            em.close();
        }
        return entity;
    }

    //generique override of function getAsString.
    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, TEntity entity) {
        if(entity==null){
            return "0";
        }
        try {
            return String.valueOf(entity.getId());
        }catch(Exception e){
            return "0";
        }
    }

}
