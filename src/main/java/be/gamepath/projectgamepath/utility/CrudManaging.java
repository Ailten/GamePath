package be.gamepath.projectgamepath.utility;

import be.gamepath.projectgamepath.enumeration.Crud;
import be.gamepath.projectgamepath.managedBeans.ConnectionBean;
import be.gamepath.projectgamepath.managedBeans.HistoricalBean;
import be.gamepath.projectgamepath.managedBeans.PopUpMessageBean;

import javax.inject.Inject;

public class CrudManaging<TEntity> {

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
    public boolean isModeSelected(Crud modeAsk){
        return this.modeSelected == modeAsk;
    }


    //object product load from other page.
    protected TEntity elementCrudSelected = null;
    public TEntity getElementCrudSelected(){
        return elementCrudSelected;
    }
    public void setElementCrudSelected(TEntity elementCrudSelected){ this.elementCrudSelected = elementCrudSelected; }


    @Inject
    ConnectionBean connectionBean;
    @Inject
    protected PopUpMessageBean popUpMessageBean;
    protected String nameEntityForPermission;
    public String submitCrudForm(HistoricalBean historicalBean){

        //verify permission user.
        String permissionExpected = this.modeSelected.getTxtValue() + "-" + nameEntityForPermission;
        if(!connectionBean.isUserHasPermission(permissionExpected)){
            //error, user have not allowed for this submit.
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.titleErrorNoPermission"),
                    Utility.stringFromI18N("application.crudPage.messageErrorNoPermission"),
                    false
            );
            return "";
        }

        //make action crud.
        boolean success = false;
        try {
            if (this.isModeSelected(Crud.CREATE))
                success = this.insert();
            else if (this.isModeSelected(Crud.UPDATE))
                success = this.update();
        }catch(Exception e){
            Utility.debug("error catch : " + e);
            success = false;
        }

        //load a success message and back to last page.
        if(success){
            popUpMessageBean.setPopUpMessage(
                    Utility.stringFromI18N("application.crudPage.errorTitleDuplicate"),
                    Utility.stringFromI18N("application.crudPage.messageSuccess"),
                    true
            );
            return historicalBean.backLastPageHistoric(); //back to last page.
        }

        return null; //if error, stay same page.
    }

    protected boolean insert() throws Exception {
        throw new Exception("CrudManaging.insert was not override");
    }
    protected boolean update() throws Exception {
        throw new Exception("CrudManaging.update was not override");
    }


/*
    private TableFilter tableFilter;
    public void setTableFilter(TableFilter tableFilter){
        this.tableFilter=tableFilter;
    }
    public void resetFilterOfTableFilter(){
        if(tableFilter==null)
            return;
        tableFilter.resetFilter();
    }


    private boolean errorSubmitDB;
    public boolean getErrorSubmitDB(){ return this.errorSubmitDB; }
    public void setErrorSubmitDB(boolean errorSubmitDB){ this.errorSubmitDB = errorSubmitDB; }
*/
}
