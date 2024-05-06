package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.Role;
import be.gamepath.projectgamepath.service.RoleService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("BasketConverter")
public class RoleConverter extends ConverterGeneric<Role, RoleService> {

    //send service to parent.
    public RoleConverter(){
        this.service = new RoleService();
    }

}
