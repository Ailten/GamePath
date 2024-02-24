package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.enumeration.Tva;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("TvaConverter")
public class TvaConverter implements Converter {

    //static cast from string to object.
    @Override
    public Tva getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("") || value.equals("null")) {
            return null;
        }

        return Tva.stringToEnum(value);

    }

    //static cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "null";
        }
        Tva tva = (Tva) value;
        return String.valueOf(tva.getTxtValue());
    }

}
