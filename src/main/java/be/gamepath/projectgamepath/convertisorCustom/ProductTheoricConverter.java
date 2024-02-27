package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.ProductTheoric;
import be.gamepath.projectgamepath.service.ProductTheoricService;
import be.gamepath.projectgamepath.utility.ConvertorGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("ProductTheoricConverter")
public class ProductTheoricConverter extends ConvertorGeneric<ProductTheoric, ProductTheoricService> {

    public ProductTheoricConverter(){
        this.service = new ProductTheoricService();
    }

    /*
    //cast from string to object.
    @Override
    public ProductTheoric getAsObject(FacesContext context, UIComponent component, String value)
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
    public static ProductTheoric getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        ProductTheoricService productTheoricService = new ProductTheoricService();
        ProductTheoric productTheoric;
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
        ProductTheoric productTheoric = (ProductTheoric) value;
        return String.valueOf(productTheoric.getId());
    }
    */
}
