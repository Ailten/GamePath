package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.enumeration.MultyPlayer;
import be.gamepath.projectgamepath.enumeration.Tva;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @Column(name = "idProductTheoric", nullable = false)
    private int idProductTheoric;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idSocietyProducer", nullable = false)
    private SocietyProducerEntity idSocietyProducer;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z -]{3,60}$")
    @Column(name = "title", nullable = false, length = 60)
    private String title;
    @NotNull
    @Min(1)
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
    private Date releaseDate;
    @NotNull
    //@Pattern(regexp = "^[a-zA-Z0-9 çéâêîôûàèìòùëïü!?.,-]{3,60}$")
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "isActive", nullable = false)
    private byte isActive;

    public int getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(int idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    public SocietyProducerEntity getIdSocietyProducer() {
        return idSocietyProducer;
    }

    public void setIdSocietyProducer(SocietyProducerEntity idSocietyProducer) {
        this.idSocietyProducer = idSocietyProducer;
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
