package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.connexion.EMF;
import be.gamepath.projectgamepath.entities.Category;
import be.gamepath.projectgamepath.entities.Language;
import be.gamepath.projectgamepath.entities.OperatingSystem;
import be.gamepath.projectgamepath.entities.ProductTheoric;
import be.gamepath.projectgamepath.service.ProductTheoricService;
import be.gamepath.projectgamepath.utility.TableFilter;
import be.gamepath.projectgamepath.utility.Utility;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;

@Named
@SessionScoped
public class ProductTheoricListBean extends TableFilter<ProductTheoric> implements Serializable {

    public void doResearch() {

        EntityManager em = EMF.createEM();
        ProductTheoricService productTheoricService = new ProductTheoricService();

        try{
            int filterCategoryId = (this.filterCategory == null? 0: this.filterCategory.getId());
            int filterLanguageId = (this.filterLanguage == null? 0: this.filterLanguage.getId());
            int filterOperatingSystemId = (this.filterOperatingSystem == null? 0: this.filterOperatingSystem.getId());

            this.entityFiltered = productTheoricService.selectManyByFilter(
                    em,
                    this.filter,
                    this.filterPriceMax,
                    filterCategoryId,
                    filterLanguageId,
                    filterOperatingSystemId,
                    connectionBean.isUserHasPermission("readList-product-disable")
            );
        }catch(Exception e){
            Utility.debug("error into doResearch : " + e.getMessage());
            this.entityFiltered = new ArrayList<>();
        }finally{
            em.close();
        }

    }


    @Min(0)
    private float filterPriceMax = 0.0f;
    public float getFilterPriceMax() {
        return filterPriceMax;
    }
    public void setFilterPriceMax(float filterPriceMax) {
        this.filterPriceMax = filterPriceMax;
    }

    private Category filterCategory = null;
    public Category getFilterCategory() {
        return filterCategory;
    }
    public void setFilterCategory(Category filterCategory) {
        this.filterCategory = filterCategory;
    }

    private Language filterLanguage = null;
    public Language getFilterLanguage() {
        return filterLanguage;
    }
    public void setFilterLanguage(Language filterLanguage) {
        this.filterLanguage = filterLanguage;
    }

    private OperatingSystem filterOperatingSystem = null;
    public OperatingSystem getFilterOperatingSystem() {
        return filterOperatingSystem;
    }
    public void setFilterOperatingSystem(OperatingSystem filterOperatingSystem) {
        this.filterOperatingSystem = filterOperatingSystem;
    }


}
