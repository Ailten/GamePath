package be.gamepath.projectgamepath.managedBeans;


import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.Role;
import be.gamepath.projectgamepath.entities.User;
import be.gamepath.projectgamepath.service.PermissionService;
import be.gamepath.projectgamepath.service.RoleService;
import be.gamepath.projectgamepath.service.UserService;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;

@Named
@SessionScoped
public class RoleBean implements Serializable {

    /**
     * Initialize list permission into a role.
     */
    public static void initListPermission(Role role)
    {
        EntityManager em = EMF.getEM();
        PermissionService permissionService = new PermissionService();

        try
        {
            role.setListPermission(permissionService.selectManyByIdRole(em, role.getId()));
        }
        catch(Exception e)
        {
            Utility.debug("error into initListPermission : " + e.getMessage());
            role.setListPermission(new ArrayList<>());
        }
        finally
        {
            em.close();
        }
    }

}
