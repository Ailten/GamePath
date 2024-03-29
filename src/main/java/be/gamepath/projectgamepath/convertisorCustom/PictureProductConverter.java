package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.PictureProduct;
import be.gamepath.projectgamepath.service.PictureProductService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("PictureProductConverter")
public class PictureProductConverter extends ConverterGeneric<PictureProduct, PictureProductService> {

    public PictureProductConverter(){
        this.service = new PictureProductService();
    }

    /*
    //cast from string to object.
    @Override
    public PictureProduct getAsObject(FacesContext context, UIComponent component, String value)
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
    public static PictureProduct getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        PictureProductService pictureProductService = new PictureProductService();
        PictureProduct pictureProduct;
        try{
            pictureProduct = pictureProductService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            pictureProduct = null;
        }finally{
            em.close();
        }
        return pictureProduct;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        PictureProduct pictureProduct = (PictureProduct) value;
        return String.valueOf(pictureProduct.getId());
    }
    */
}
