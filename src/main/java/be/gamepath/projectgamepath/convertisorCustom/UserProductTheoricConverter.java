package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.UserProductTheoric;
import be.gamepath.projectgamepath.service.UserProductTheoricService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("UserProductTheoricConverter")
public class UserProductTheoricConverter extends ConverterGeneric<UserProductTheoric, UserProductTheoricService> {

    //send service to parent.
    public UserProductTheoricConverter(){
        this.service = new UserProductTheoricService();
    }

}
