package be.gamepath.projectgamepath.managedBeans;

import org.primefaces.PrimeFaces;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named
@SessionScoped
public class I18nBean implements Serializable {

    @Inject
    ConnectionBean connectionBean;

    private Locale locale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    private void loadLocal(){
        locale = new Locale(this.language);
    }

    public void loadLanguagePage(){
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        viewRoot.setLocale(locale);
        FacesContext.getCurrentInstance().getApplication().setDefaultLocale(locale);
    }

    public void changeLanguage(String language){
        //apply and load new language.
        this.setLanguage(language);
        this.loadLocal();
        this.loadLanguagePage();

        //call js function to submit form after execution.
        connectionBean.submitForm("");
        PrimeFaces.current().executeScript("submitFormById(\"header-language-button-container\")");
    }

}
