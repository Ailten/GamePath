package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.Permission;
import be.gamepath.projectgamepath.service.PermissionService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("PermissionConverter")
public class PermissionConverter extends ConverterGeneric<Permission, PermissionService> {

    public PermissionConverter(){
        this.service = new PermissionService();
    }

    /*
    //cast from string to object.
    @Override
    public Permission getAsObject(FacesContext context, UIComponent component, String value)
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
    public static Permission getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        PermissionService permissionService = new PermissionService();
        Permission permission;
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
        Permission permission = (Permission) value;
        return String.valueOf(permission.getId());
    }
    */
}
