package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.OperatingSystem;
import be.gamepath.projectgamepath.service.OperatingSystemService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("OperatingSystemConverter")
public class OperatingSystemConverter extends ConverterGeneric<OperatingSystem, OperatingSystemService> {

    public OperatingSystemConverter(){
        this.service = new OperatingSystemService();
    }

    /*
    //cast from string to object.
    @Override
    public OperatingSystem getAsObject(FacesContext context, UIComponent component, String value)
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
    public static OperatingSystem getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        OperatingSystemService operatingSystemService = new OperatingSystemService();
        OperatingSystem operatingSystem;
        try{
            operatingSystem = operatingSystemService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            operatingSystem = null;
        }finally{
            em.close();
        }
        return operatingSystem;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        OperatingSystem operatingSystem = (OperatingSystem) value;
        return String.valueOf(operatingSystem.getId());
    }
    */
}
