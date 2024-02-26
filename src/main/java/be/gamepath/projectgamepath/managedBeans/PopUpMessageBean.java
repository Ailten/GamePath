package be.gamepath.projectgamepath.managedBeans;

import org.primefaces.PrimeFaces;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class PopUpMessageBean implements Serializable {

    public void setPopUpMessage(String title, String message, boolean isSuccess){

        //send pop-up-message to facesMessage.
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            (isSuccess? FacesMessage.SEVERITY_INFO: FacesMessage.SEVERITY_ERROR),
            title,
            message
        ));

        //update pop-up-message.
        PrimeFaces.current().ajax().update("form:pop-up-message", "form-pop-up-message");

    }

}
