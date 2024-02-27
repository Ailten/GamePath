package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.SocietyProducer;
import be.gamepath.projectgamepath.service.SocietyProducerService;
import be.gamepath.projectgamepath.utility.ConvertorGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("SocietyProducerConverter")
public class SocietyProducerConverter extends ConvertorGeneric<SocietyProducer, SocietyProducerService> {

    public SocietyProducerConverter(){
        this.service = new SocietyProducerService();
    }

    /*
    //cast from string to object.
    @Override
    public SocietyProducer getAsObject(FacesContext context, UIComponent component, String value)
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
    public static SocietyProducer getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        SocietyProducerService societyProducerService = new SocietyProducerService();
        SocietyProducer societyProducer;
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
        SocietyProducer societyProducer = (SocietyProducer) value;
        return String.valueOf(societyProducer.getId());
    }
    */
}
