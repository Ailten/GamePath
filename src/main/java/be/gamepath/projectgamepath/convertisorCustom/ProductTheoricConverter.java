package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.ProductTheoricEntity;
import be.gamepath.projectgamepath.service.ProductTheoricService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("ProductTheoricConverter")
public class ProductTheoricConverter implements Converter {

    //cast from string to object.
    @Override
    public ProductTheoricEntity getAsObject(FacesContext context, UIComponent component, String value)
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
    public static ProductTheoricEntity getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        ProductTheoricService productTheoricService = new ProductTheoricService();
        ProductTheoricEntity productTheoric;
        try{
            productTheoric = productTheoricService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            productTheoric = null;
        }finally{
            em.close();
        }
        return productTheoric;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        ProductTheoricEntity productTheoric = (ProductTheoricEntity) value;
        return String.valueOf(productTheoric.getIdProductTheoric());
    }

}
