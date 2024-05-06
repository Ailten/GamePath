package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.OperatingSystem;
import be.gamepath.projectgamepath.service.OperatingSystemService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("OperatingSystemConverter")
public class OperatingSystemConverter extends ConverterGeneric<OperatingSystem, OperatingSystemService> {

    //send service to parent.
    public OperatingSystemConverter(){
        this.service = new OperatingSystemService();
    }

}
