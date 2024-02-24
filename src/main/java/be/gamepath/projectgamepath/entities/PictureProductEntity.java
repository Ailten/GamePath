package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "PictureProductEntity.SelectById",
                query = "select pp from PictureProductEntity pp " +
                        "where (pp.idPictureProduct = :id)"),
        @NamedQuery(name= "PictureProductEntity.SelectMany",
                query = "select pp from PictureProductEntity pp "),
})
@Entity
@Table(name = "pictureproduct", schema = "gamepath", catalog = "")
public class PictureProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idPictureProduct", nullable = false)
    private int idPictureProduct;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoricEntity idProductTheoric;
    @NotNull
    @Pattern(regexp = "^http://") //canvas : http://localhost/imageFolderLocalHost/******.png
    @Column(name = "urlImage", nullable = false, length = 255)
    private String urlImage;

    public int getIdPictureProduct() {
        return idPictureProduct;
    }

    public void setIdPictureProduct(int idPictureProduct) {
        this.idPictureProduct = idPictureProduct;
    }

    public ProductTheoricEntity getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(ProductTheoricEntity idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureProductEntity that = (PictureProductEntity) o;
        return idPictureProduct == that.idPictureProduct;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPictureProduct, idProductTheoric, urlImage);
    }
}
