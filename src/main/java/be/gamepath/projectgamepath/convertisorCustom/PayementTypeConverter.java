package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.enumeration.PayementType;
import be.gamepath.projectgamepath.enumeration.Tva;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("PayementTypeConverter")
public class PayementTypeConverter implements Converter {

    @Override
    public PayementType getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("") || value.equals("null")) {
            return null;
        }

        return PayementType.stringToEnum(value);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "null";
        }
        PayementType payementType = (PayementType) value;
        return String.valueOf(payementType.getTxtValue());
    }

}
