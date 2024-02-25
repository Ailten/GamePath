package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.User;
import be.gamepath.projectgamepath.service.UserService;
import be.gamepath.projectgamepath.utility.Utility;
import org.primefaces.PrimeFaces;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@Named
@SessionScoped
public class ConnectionBean implements Serializable {

    //user connected.
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


    /**
     * Connect an user.
     */
    public String connection(String login) {

        EntityManager em = EMF.getEM();
        UserService userService = new UserService();
        EntityTransaction transaction = em.getTransaction();
        boolean isError = false;

        this.user = new User();

        try{
            transaction.begin();
            this.user = userService.selectUserByLogin(em, login);
            transaction.commit();
        }
        catch(Exception e)
        {
            isError = true;
        }
        finally
        {
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }

        if(isError)
            return "";

        return "/accueil";
    }

    /**
     * Deconnect an user.
     */
    public String deconnection() {
        // Logout session connectionBean
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        // managed bean go to js
        PrimeFaces.current().executeScript("submitLanguageForm(\"headerLanguageButtonContainer\")");
        return "/accueil";
    }

    /**
     * ask if this user is connected.
     */
    public boolean isUserHasThisLogin(String login) {
        if(this.user == null)
            return false;
        return this.user.getEmail().equals(login);
    }


    /**
     * Initialize list role into an user
     */
    public static void initListRolePermission(User user)
    {
        EntityManager em = EMF.getEM();
        UserService userService = new UserService();
        EntityTransaction transaction = em.getTransaction();
        try
        {
            transaction.begin();
            //Call of the service that will use the NamedQuery of the "RolePermission" entity.
            user.listRolePermission = userService.selectRolePermissionOfUser(em, user.getId());
            transaction.commit();
        }
        catch(Exception e)
        {
            Utility.debug("error into initListPermissionOfUser : " + e);
        }
        finally
        {
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }

    }


    //ask is user log has permissions send.
    public boolean verifyPermissionUser(String permissionName){
        if(this.user == null || this.user.getId()==0)
            return false;
        return this.user.verifyPermission(permissionName);
    }

    public boolean verifyNotPermissionUser(String permissionName){
        return !(verifyPermissionUser(permissionName));
    }

    /**
     * Redirect to another page.
     */
    public String redirectPage(String path) {
        return path;
    }


    //return a string with concat last name and first name of user.
    public String getFullNameUser(){
        if(this.user == null)
            return "";
        return (this.user.getLastName()+" "+this.user.getFirstName());
    }

    //return if the user has a permission.
    public boolean isUserHasPermission(String permissionAsk){
        if(this.user == null)
            return false;
        return this.user.verifyPermission(permissionAsk);
    }

}
