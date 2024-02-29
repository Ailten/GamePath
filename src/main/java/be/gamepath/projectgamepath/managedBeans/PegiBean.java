package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.entities.Pegi;
import be.gamepath.projectgamepath.service.CategoryService;
import be.gamepath.projectgamepath.service.PegiService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class PegiBean implements Serializable {

    //list all pegi for select input.
    private List<Pegi> allPegi;
    public List<Pegi> getAllPegi(){
        return this.allPegi;
    }
    public void initAllPegi(){
        EntityManager em = EMF.getEM();
        PegiService pegiService = new PegiService();
        try{
            this.allPegi = pegiService.selectMany(em);
        }catch(Exception e){
            this.allPegi = new ArrayList<>();
        }finally{
            em.close();
        }
    }

}
