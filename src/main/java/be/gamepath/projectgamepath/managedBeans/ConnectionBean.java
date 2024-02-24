package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.UserEntity;
import be.gamepath.projectgamepath.service.RoleService;
import be.gamepath.projectgamepath.service.UserService;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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


    /**
     * Initialize list role into an user
     */
    public static void initListRolePermission(UserEntity user)
    {
        EntityManager em = EMF.getEM();
        UserService userService = new UserService();
        EntityTransaction transaction = em.getTransaction();
        try
        {
            transaction.begin();
            //Call of the service that will use the NamedQuery of the "RolePermission" entity.
            user.listRolePermission = userService.selectRolePermissionOfUser(em, user.getIdUser().getId());
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
        if(this.userConnected == null || this.userConnected.getIdUser()==0)
            return false;
        return this.userConnected.verifyPermission(permissionName);
    }

    public boolean verifyNotPermissionUser(String permissionName){
        return !(verifyPermissionUser(permissionName));
    }

}
