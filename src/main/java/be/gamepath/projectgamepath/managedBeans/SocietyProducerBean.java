package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.SocietyProducer;
import be.gamepath.projectgamepath.service.SocietyProducerService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class SocietyProducerBean implements Serializable {

    //list editor for select input.
    private List<SocietyProducer> allSocietyProducer;
    public List<SocietyProducer> getAllSocietyProducer(){
        return this.allSocietyProducer;
    }
    public void initAllSocietyProducer(){
        EntityManager em = EMF.createEM();
        SocietyProducerService editorService = new SocietyProducerService();
        try{
            this.allSocietyProducer = editorService.selectMany(em);
        }catch(Exception e){
            this.allSocietyProducer = new ArrayList<>();
        }finally{
            em.close();
        }
    }

}
