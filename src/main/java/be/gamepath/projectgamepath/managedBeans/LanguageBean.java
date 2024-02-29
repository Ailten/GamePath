package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.entities.Language;
import be.gamepath.projectgamepath.service.CategoryService;
import be.gamepath.projectgamepath.service.LanguageService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class LanguageBean implements Serializable {

    //list all language for select input.
    private List<Language> allLanguage;
    public List<Language> getAllLanguage(){
        return this.allLanguage;
    }
    public void initAllLanguage(){
        EntityManager em = EMF.getEM();
        LanguageService languageService = new LanguageService();
        try{
            this.allLanguage = languageService.selectMany(em);
        }catch(Exception e){
            this.allLanguage = new ArrayList<>();
        }finally{
            em.close();
        }
    }

}
