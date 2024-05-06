package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.Language;
import be.gamepath.projectgamepath.service.LanguageService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("LanguageConverter")
public class LanguageConverter extends ConverterGeneric<Language, LanguageService> {

    //send service to parent.
    public LanguageConverter(){
        this.service = new LanguageService();
    }

}
