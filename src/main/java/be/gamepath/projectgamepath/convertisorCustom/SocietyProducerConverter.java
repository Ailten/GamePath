package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.SocietyProducer;
import be.gamepath.projectgamepath.service.SocietyProducerService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("SocietyProducerConverter")
public class SocietyProducerConverter extends ConverterGeneric<SocietyProducer, SocietyProducerService> {

    //send service to parent.
    public SocietyProducerConverter(){
        this.service = new SocietyProducerService();
    }

}
