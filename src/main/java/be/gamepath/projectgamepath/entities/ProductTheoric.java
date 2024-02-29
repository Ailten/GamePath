package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.enumeration.MultyPlayer;
import be.gamepath.projectgamepath.enumeration.Tva;
import be.gamepath.projectgamepath.managedBeans.ProductTheoricBean;
import be.gamepath.projectgamepath.utility.EntityGenerique;
import be.gamepath.projectgamepath.utility.Utility;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductTheoric.SelectById",
                query = "select pt from ProductTheoric pt " +
                        "where (pt.id = :id)"),
        @NamedQuery(name= "ProductTheoric.SelectMany",
                query = "select pt from ProductTheoric pt "),
        @NamedQuery(name= "ProductTheoric.SelectByTitle",
                query = "select pt from ProductTheoric pt " +
                        "where (pt.title = :title)"),
})
@Entity
@Table(name = "producttheoric", schema = "gamepath", catalog = "")
public class ProductTheoric extends EntityGenerique {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProductTheoric", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idSocietyProducer", nullable = false)
    private SocietyProducer societyProducer;

    @NotNull
    @Size(min = 3, max = 60) //message="I18N[application.crudPage.errorStringSize60]"
    @Pattern(regexp = "^[a-zA-Z -]{3,60}$")
    @Column(name = "title", nullable = false, length = 60)
    private String title;
    @NotNull
    @Min(0)
    @Column(name = "priceHTVA", nullable = false)
    private float priceHtva;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TVA", nullable = false)
    private Tva tva;
    @NotNull
    @Min(0)
    @Max(90)
    @Column(name = "reduction", nullable = false)
    private int reduction;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "multiPlayer", nullable = false)
    private MultyPlayer multiPlayer;
    @NotNull
    @Column(name = "releaseDate", nullable = false)
    private LocalDateTime releaseDate;
    @NotNull
    @Size(min = 3, max = 255)
    //@Pattern(regexp = "^[a-zA-Z0-9 çéâêîôûàèìòùëïü!?.,-]{3,255}$")
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SocietyProducer getSocietyProducer() {
        return societyProducer;
    }

    public void setSocietyProducer(SocietyProducer societyProducer) {
        this.societyProducer = societyProducer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPriceHtva() {
        return priceHtva;
    }

    public void setPriceHtva(float priceHtva) {
        this.priceHtva = priceHtva;
    }

    public Tva getTva() {
        return tva;
    }

    public void setTva(Tva tva) {
        this.tva = tva;
    }

    public int getReduction() {
        return reduction;
    }

    public void setReduction(int reduction) {
        this.reduction = reduction;
    }

    public MultyPlayer getMultiPlayer() {
        return multiPlayer;
    }

    public void setMultiPlayer(MultyPlayer multiPlayer) {
        this.multiPlayer = multiPlayer;
    }

    public Date getReleaseDate() {
        if(this.releaseDate == null)
            return null;
        return Utility.castLocalDateTimeToDate(this.releaseDate);
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = Utility.castDateToLocalDateTime(releaseDate);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTheoric that = (ProductTheoric) o;
        return id == that.id;
    }
    */

    @Override
    public int hashCode() {
        return Objects.hash(id, societyProducer, title, priceHtva, tva, reduction, multiPlayer, releaseDate, description, isActive);
    }


    public float getPrice(){
        if(this.tva == null)
            return this.priceHtva;
        return this.priceHtva + this.tva.evalTva(this.priceHtva);
    }

    public boolean hasReduction(){
        return this.reduction != 0;
    }

    public float getPriceWithReduction(){
        return this.getPrice() * (1 - ((float)this.reduction)/100);
    }



    @Transient
    private List<Category> listCategory;
    public List<Category> getListCategory() {
        if (this.listCategory == null)
            ProductTheoricBean.initListCategory(this);
        return this.listCategory;
    }
    public void setListCategory(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    @Transient
    private List<Pegi> listPegi;
    public List<Pegi> getListPegi() {
        if (this.listPegi == null)
            ProductTheoricBean.initListPegi(this);
        return this.listPegi;
    }
    public void setListPegi(List<Pegi> listPegi) {
        this.listPegi = listPegi;
    }

    @Transient
    private List<OperatingSystem> listOperatingSystem;
    public List<OperatingSystem> getListOperatingSystem() {
        if (this.listOperatingSystem == null)
            ProductTheoricBean.initListOperatingSystem(this);
        return this.listOperatingSystem;
    }
    public void setListOperatingSystem(List<OperatingSystem> listOperatingSystem) {
        this.listOperatingSystem = listOperatingSystem;
    }

    @Transient
    private List<Language> listLanguage;
    public List<Language> getListLanguage() {
        if (this.listLanguage == null)
            ProductTheoricBean.initListLanguage(this);
        return this.listLanguage;
    }
    public void setListLanguage(List<Language> listLanguage) {
        this.listLanguage = listLanguage;
    }

    @Transient
    private List<PictureProduct> listPictureProduct;
    public List<PictureProduct> getListPictureProduct() {
        if (this.listPictureProduct == null)
            ProductTheoricBean.initListPictureProduct(this);
        return this.listPictureProduct;
    }
    public void setListPictureProduct(List<PictureProduct> listPictureProduct) {
        this.listPictureProduct = listPictureProduct;
    }

}
