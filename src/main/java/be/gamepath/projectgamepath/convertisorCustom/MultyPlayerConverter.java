package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.enumeration.MultyPlayer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("MultyPlayerConverter")
public class MultyPlayerConverter implements Converter {

    //static cast from string to object.
    @Override
    public MultyPlayer getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("") || value.equals("null")) {
            return null;
        }

        return MultyPlayer.stringToEnum(value);

    }

    //static cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "null";
        }
        MultyPlayer multyPlayer = (MultyPlayer) value;
        return String.valueOf(multyPlayer.getTxtValue());
    }

}
