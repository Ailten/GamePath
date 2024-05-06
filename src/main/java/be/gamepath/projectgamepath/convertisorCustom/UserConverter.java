package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.User;
import be.gamepath.projectgamepath.service.UserService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("UserConverter")
public class UserConverter extends ConverterGeneric<User, UserService> {

    //send service to parent.
    public UserConverter(){
        this.service = new UserService();
    }

}
