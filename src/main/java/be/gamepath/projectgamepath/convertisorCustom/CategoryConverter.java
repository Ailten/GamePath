package be.gamepath.projectgamepath.convertisorCustom;

import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.service.CategoryService;
import be.gamepath.projectgamepath.utility.ConverterGeneric;

import javax.faces.convert.FacesConverter;

@FacesConverter("CategoryConverter")
public class CategoryConverter extends ConverterGeneric<Category, CategoryService> {

    //send service to parent.
    public CategoryConverter(){
        this.service = new CategoryService();
    }

}

