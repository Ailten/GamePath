package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.RoleEntity;
import be.gamepath.projectgamepath.service.RoleService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("BasketConverter")
public class RoleConverter implements Converter {

    //cast from string to object.
    @Override
    public RoleEntity getAsObject(FacesContext context, UIComponent component, String value)
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
    public static RoleEntity getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        RoleService roleService = new RoleService();
        RoleEntity role;
        try{
            role = roleService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            role = null;
        }finally{
            em.close();
        }
        return role;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        RoleEntity role = (RoleEntity) value;
        return String.valueOf(role.getIdRole());
    }

}
