package be.gamepath.projectgamepath.utility;

import be.gamepath.projectgamepath.enumeration.Crud;
import be.gamepath.projectgamepath.managedBeans.ConnectionBean;
import be.gamepath.projectgamepath.managedBeans.HistoricalBean;
import be.gamepath.projectgamepath.managedBeans.PopUpMessageBean;

import javax.inject.Inject;

public class CrudManaging<TEntity extends EntityGenerique> {

    @Inject
    protected ConnectionBean connectionBean;
    @Inject
    protected PopUpMessageBean popUpMessageBean;

    //char for type of page generate.
    protected Crud modeSelected = Crud.READ_LIST;

    public Crud getModeSelected() { return this.modeSelected; }
    public void setModeSelected(Crud mode) { this.modeSelected = mode; }
    public boolean isModeSelected(String... modesAskStr){
        for(String modeAskStr: modesAskStr){
            if(this.isModeSelected(modeAskStr))
                return true;
        }
        return false;
    }
    public boolean isModeSelected(String modeAskStr){
        return this.isModeSelected(Crud.getFromStr(modeAskStr));
    }
    public boolean isModeSelected(Crud... modesAsk){
        for(Crud modeAskStr: modesAsk){
            if(this.isModeSelected(modeAskStr))
                return true;
        }
        return false;
    }
    public boolean isModeSelected(Crud modeAsk){
        return this.modeSelected == modeAsk;
    }


    //object product load from other page.
    protected TEntity elementCrudSelected = null;
    public TEntity getElementCrudSelected(){
        return elementCrudSelected;
    }
    public void setElementCrudSelected(TEntity elementCrudSelected){ this.elementCrudSelected = elementCrudSelected; }


    //string name of table for concatenation of permission.
    protected String nameEntityForPermission;

    //generic function of submit crud form.
    private boolean trySubmitCrudForm(){

        //verify permission user.
        String permissionExpected = this.modeSelected.getTxtValue() + "-" + nameEntityForPermission;
        if(!connectionBean.isUserHasPermission(permissionExpected)){
            //error, user have not allowed for this submit.
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.titleErrorNoPermission"),
                    Utility.stringFromI18N("application.crudPage.messageErrorNoPermission"),
                    false
            );
            return false;
        }

        //make action crud.
        boolean isSuccess = false;
        try {
            if (this.isModeSelected(Crud.CREATE))
                isSuccess = this.insert();
            else if (this.isModeSelected(Crud.UPDATE))
                isSuccess = this.update();
        }catch(Exception e){
            Utility.debug("error into submitCrudForm : " + e.getMessage());
            isSuccess = false;
        }

        //load a success message and back to last page.
        if(isSuccess){
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.titleSuccess"),
                    Utility.stringFromI18N("application.crudPage.messageSuccess"),
                    true
            );
            return true; //back to last page.
        }

        return false; //if error, stay same page.
    }
    public String submitCrudForm(HistoricalBean historicalBean){
        return (trySubmitCrudForm()? historicalBean.backLastPageHistoric(): null);
    }
    public String submitCrudForm(String redirectPage){
        return (trySubmitCrudForm()? redirectPage: null);
    }
    public String submitCrudForm(){
        return (trySubmitCrudForm()? connectionBean.getPathHomePage(): null);
    }

    //function insert (need override for use trySubmitCrudForm with Create mode).
    protected boolean insert() throws Exception {
        throw new Exception("CrudManaging.insert was not override");
    }

    //function update (need override for use trySubmitCrudForm with Update mode).
    protected boolean update() throws Exception {
        throw new Exception("CrudManaging.update was not override");
    }

}
