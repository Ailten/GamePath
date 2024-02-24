package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductTheoricEntity.SelectById",
                query = "select pt from ProductTheoricEntity pt " +
                        "where (pt.idProductTheoric = :id)"),
        @NamedQuery(name= "ProductTheoricEntity.SelectMany",
                query = "select pt from ProductTheoricEntity pt "),
})
@Entity
@Table(name = "producttheoric", schema = "gamepath", catalog = "")
public class ProductTheoricEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProductTheoric")
    private int idProductTheoric;
    @Basic
    @Column(name = "idSocietyProducer")
    private int idSocietyProducer;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "priceHTVA")
    private double priceHtva;
    @Basic
    @Column(name = "TVA")
    private Object tva;
    @Basic
    @Column(name = "reduction")
    private int reduction;
    @Basic
    @Column(name = "multiPlayer")
    private Object multiPlayer;
    @Basic
    @Column(name = "releaseDate")
    private Date releaseDate;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "isActive")
    private byte isActive;

    public int getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(int idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    public int getIdSocietyProducer() {
        return idSocietyProducer;
    }

    public void setIdSocietyProducer(int idSocietyProducer) {
        this.idSocietyProducer = idSocietyProducer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPriceHtva() {
        return priceHtva;
    }

    public void setPriceHtva(double priceHtva) {
        this.priceHtva = priceHtva;
    }

    public Object getTva() {
        return tva;
    }

    public void setTva(Object tva) {
        this.tva = tva;
    }

    public int getReduction() {
        return reduction;
    }

    public void setReduction(int reduction) {
        this.reduction = reduction;
    }

    public Object getMultiPlayer() {
        return multiPlayer;
    }

    public void setMultiPlayer(Object multiPlayer) {
        this.multiPlayer = multiPlayer;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getIsActive() {
        return isActive;
    }

    public void setIsActive(byte isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTheoricEntity that = (ProductTheoricEntity) o;
        return idProductTheoric == that.idProductTheoric;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductTheoric, idSocietyProducer, title, priceHtva, tva, reduction, multiPlayer, releaseDate, description, isActive);
    }
}
