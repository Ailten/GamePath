package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.service.CategoryService;
import be.gamepath.projectgamepath.utility.ConvertorGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("CategoryConverter")
public class CategoryConverter extends ConvertorGeneric<Category, CategoryService> {

    public CategoryConverter(){
        this.service = new CategoryService();
    }

    /*
    //cast from string to object.
    @Override
    public Category getAsObject(FacesContext context, UIComponent component, String value)
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
    public static Category getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        CategoryService categoryService = new CategoryService();
        Category category;
        try{
            category = categoryService.selectById(em, Integer.parseInt(value));
        }catch(Exception e){
            category = null;
        }finally{
            em.close();
        }
        return category;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        Category category = (Category) value;
        return String.valueOf(category.getId());
    }
    */

}

