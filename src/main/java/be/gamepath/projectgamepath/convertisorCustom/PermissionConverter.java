package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.Permission;
import be.gamepath.projectgamepath.service.PermissionService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("PermissionConverter")
public class PermissionConverter extends ConverterGeneric<Permission, PermissionService> {

    //send service to parent.
    public PermissionConverter(){
        this.service = new PermissionService();
    }

}
