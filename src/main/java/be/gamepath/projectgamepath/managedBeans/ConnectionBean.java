package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.User;
import be.gamepath.projectgamepath.enumeration.Crud;
import be.gamepath.projectgamepath.service.UserService;
import be.gamepath.projectgamepath.utility.CrudManaging;
import be.gamepath.projectgamepath.utility.EntityGenerique;
import be.gamepath.projectgamepath.utility.Utility;
import org.primefaces.PrimeFaces;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.rmi.CORBA.Util;
import java.io.Serializable;
import java.lang.reflect.Type;

@Named
@SessionScoped
public class ConnectionBean implements Serializable {

    @Inject
    PopUpMessageBean popUpMessageBean;
    @Inject
    HistoricalBean historicalBean;

    private static String pathHomePage = "/accueil";
    public String getPathHomePage() { return pathHomePage; }

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
        boolean isError = false;

        this.user = new User();

        try{

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

        }catch(Exception e){
            isError = true;
            this.user = null;
        }finally{
            em.close();
        }

        if(isError)
            return null;

        return this.redirectPage(pathHomePage);
    }

    /**
     * Deconnect an user.
     */
    public String deconnection() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); //Logout session connectionBean.
        return this.redirectPage(pathHomePage);
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
     * Redirect to another page (if redirect to same page, reload it).
     */
    public String redirectPage(String path) {
        if(path == null || path.equals("")) {
            //this.reloadPage();
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.header.titleRedirectPageNotFound"),
                    Utility.stringFromI18N("application.header.errorRedirectPageNotFound"),
                    false
            );
        }
        //}else if(historicalBean.currentPage().equals(path)){
        //    this.reloadPage(); //reload if same page.
        //}
        return path;
    }

    /**
     * Redirect to another page and send an entity selected.
     * @param path entity manager.
     * @return list entity match.
     */
    public String redirectCrudPage(String path, CrudManaging<EntityGenerique> crudManaging, EntityGenerique entitySelectedForCrud, String modeAskStr) {
        crudManaging.setElementCrudSelected(entitySelectedForCrud);
        crudManaging.setModeSelected(Crud.getFromStr(modeAskStr));

        return redirectPage(path);
    }

    private Type crud = Crud.class;
    public Type getCrud() { return this.crud; };


    //return a string with concat last name and first name of user.
    public String getFullNameUser(){
        if(this.user == null)
            return "";
        return (this.user.getLastName()+" "+this.user.getFirstName());
    }

    //return if the user has a permission.
    public boolean isUserHasPermission(String permissionAskStr){
        if(this.user == null)
            return false;
        return this.user.verifyPermission(permissionAskStr);
    }


    //submit form by id.
    public void submitForm(String idForm){
        PrimeFaces.current().executeScript("submitFormById(\""+idForm+"\")");
    }

    public void reloadPage(){
        PrimeFaces.current().executeScript("reloadPage()");
    }


}
