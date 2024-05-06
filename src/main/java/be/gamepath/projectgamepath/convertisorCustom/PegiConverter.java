package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.Pegi;
import be.gamepath.projectgamepath.service.PegiService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("PegiConverter")
public class PegiConverter extends ConverterGeneric<Pegi, PegiService> {

    //send service to parent.
    public PegiConverter(){
        this.service = new PegiService();
    }

}
