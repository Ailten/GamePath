package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.entities.UserEntity;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class ConnectionBean implements Serializable {

    private UserEntity userConnected;

    public UserEntity getUser() {
        return userConnected;
    }
    public void setUser(UserEntity user) {
        this.userConnected = user;
    }


    //connection() //TODO.
    //destroyConnection() //TODO.


    //ask is user log has permissions send.
    public boolean verifyPermissionUser(String permissionName){
        if(this.userConnected == null || this.userConnected.getIdUser()==0)
            return false;
        //return this.userConnected.verifyPermission(permissionName); //TODO: implement list in entity user.
        return true; //patchwork temporaire.
    }

    public boolean verifyNotPermissionUser(String permissionName){
        return !(verifyPermissionUser(permissionName));
    }

}
