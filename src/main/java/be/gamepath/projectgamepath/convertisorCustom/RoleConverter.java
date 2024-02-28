package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.Role;
import be.gamepath.projectgamepath.service.RoleService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("BasketConverter")
public class RoleConverter extends ConverterGeneric<Role, RoleService> {

    public RoleConverter(){
        this.service = new RoleService();
    }

    /*
    //cast from string to object.
    @Override
    public Role getAsObject(FacesContext context, UIComponent component, String value)
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
    public static Role getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        RoleService roleService = new RoleService();
        Role role;
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
        Role role = (Role) value;
        return String.valueOf(role.getId());
    }
    */
}
