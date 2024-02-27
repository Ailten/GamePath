package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.User;
import be.gamepath.projectgamepath.service.UserService;
import be.gamepath.projectgamepath.utility.ConvertorGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("UserConverter")
public class UserConverter extends ConvertorGeneric<User, UserService> {

    public UserConverter(){
        this.service = new UserService();
    }

    /*
    //cast from string to object.
    @Override
    public User getAsObject(FacesContext context, UIComponent component, String value)
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
    public static User getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        UserService userService = new UserService();
        User user;
        try{
            user = userService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            user = null;
        }finally{
            em.close();
        }
        return user;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        User user = (User) value;
        return String.valueOf(user.getId());
    }
    */
}
