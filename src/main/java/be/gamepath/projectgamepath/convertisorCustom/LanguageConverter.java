package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.Language;
import be.gamepath.projectgamepath.service.LanguageService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("LanguageConverter")
public class LanguageConverter extends ConverterGeneric<Language, LanguageService> {

    public LanguageConverter(){
        this.service = new LanguageService();
    }

    /*
    //cast from string to object.
    @Override
    public Language getAsObject(FacesContext context, UIComponent component, String value)
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
    public static Language getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        LanguageService languageService = new LanguageService();
        Language language;
        try{
            language = languageService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            language = null;
        }finally{
            em.close();
        }
        return language;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        Language language = (Language) value;
        return String.valueOf(language.getId());
    }
    */
}
