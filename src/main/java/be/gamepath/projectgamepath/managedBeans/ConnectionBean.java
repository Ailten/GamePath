package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.User;
import be.gamepath.projectgamepath.service.UserService;
import be.gamepath.projectgamepath.utility.Utility;
import org.primefaces.PrimeFaces;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@Named
@SessionScoped
public class ConnectionBean implements Serializable {

    @Inject
    PopUpMessageBean popUpMessageBean;

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
    public String connection(String login, String password) {

        EntityManager em = EMF.getEM();
        UserService userService = new UserService();
        //EntityTransaction transaction = em.getTransaction();
        boolean isError = false;

        this.user = new User();

        try{
            //transaction.begin();
            this.user = userService.selectUserByLogin(em, login);

            if(this.user == null || //no user with this login was found in DB.
                !Utility.passwordEqualsHash(password, this.user.getPassword()) //the password was not matching to password in DB.
            ){
                popUpMessageBean.setPopUpMessage(
                        Utility.stringFromI18N("application.connexion.titleErrorUserNotFound"),
                        Utility.stringFromI18N("application.connexion.messageErrorUserNotFound"),
                        false
                );
                throw new Exception("User not found");
            }
            //transaction.commit();
        }
        catch(Exception e)
        {
            isError = true;
            this.user = null;
        }
        finally
        {
            //if(transaction.isActive())
            //    transaction.rollback();
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
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); //Logout session connectionBean.
        PrimeFaces.current().executeScript("submitLanguageForm(\"headerLanguageButtonContainer\")"); //managed bean go to js.
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
