package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.ProductKeyEntity;
import be.gamepath.projectgamepath.service.ProductKeyService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("ProductKeyConverter")
public class ProductKeyConverter implements Converter {

    //cast from string to object.
    @Override
    public ProductKeyEntity getAsObject(FacesContext context, UIComponent component, String value)
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
    public static ProductKeyEntity getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        ProductKeyService productKeyService = new ProductKeyService();
        ProductKeyEntity productKey;
        try{
            productKey = productKeyService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            productKey = null;
        }finally{
            em.close();
        }
        return productKey;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        ProductKeyEntity productKey = (ProductKeyEntity) value;
        return String.valueOf(productKey.getIdProductKey());
    }

}
