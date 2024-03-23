package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.OperatingSystem;
import be.gamepath.projectgamepath.service.OperatingSystemService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class OperatingSystemBean implements Serializable {

    //list all operatingSystem for select input.
    private List<OperatingSystem> allOperatingSystem;
    public List<OperatingSystem> getAllOperatingSystem(){
        return this.allOperatingSystem;
    }
    public void initAllOperatingSystem(){
        EntityManager em = EMF.createEM();
        OperatingSystemService operatingSystemService = new OperatingSystemService();
        try{
            this.allOperatingSystem = operatingSystemService.selectMany(em);
        }catch(Exception e){
            this.allOperatingSystem = new ArrayList<>();
        }finally{
            em.close();
        }
    }

}
