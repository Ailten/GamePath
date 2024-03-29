package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.ProductKey;
import be.gamepath.projectgamepath.service.ProductKeyService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("ProductKeyConverter")
public class ProductKeyConverter extends ConverterGeneric<ProductKey, ProductKeyService> {

    public ProductKeyConverter(){
        this.service = new ProductKeyService();
    }

    /*
    //cast from string to object.
    @Override
    public ProductKey getAsObject(FacesContext context, UIComponent component, String value)
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
    public static ProductKey getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        ProductKeyService productKeyService = new ProductKeyService();
        ProductKey productKey;
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
        ProductKey productKey = (ProductKey) value;
        return String.valueOf(productKey.getId());
    }
    */
}
