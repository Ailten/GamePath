package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.entities.SocietyProducer;
import be.gamepath.projectgamepath.service.CategoryService;
import be.gamepath.projectgamepath.service.SocietyProducerService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class CategoryBean implements Serializable {

    //list all category for select input.
    private List<Category> allCategory;
    public List<Category> getAllCategory(){
        return this.allCategory;
    }
    public void initAllCategory(){
        EntityManager em = EMF.getEM();
        CategoryService categoryService = new CategoryService();
        try{
            this.allCategory = categoryService.selectMany(em);
        }catch(Exception e){
            this.allCategory = new ArrayList<>();
        }finally{
            em.close();
        }
    }

}
