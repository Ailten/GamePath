package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.PermissionEntity;
import be.gamepath.projectgamepath.service.PermissionService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("PermissionConverter")
public class PermissionConverter implements Converter {

    //cast from string to object.
    @Override
    public PermissionEntity getAsObject(FacesContext context, UIComponent component, String value)
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
    public static PermissionEntity getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        PermissionService permissionService = new PermissionService();
        PermissionEntity permission;
        try{
            permission = permissionService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            permission = null;
        }finally{
            em.close();
        }
        return permission;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        PermissionEntity permission = (PermissionEntity) value;
        return String.valueOf(permission.getIdPermission());
    }

}
