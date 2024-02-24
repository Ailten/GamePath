package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
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
    @Column(name = "idPictureProduct")
    private int idPictureProduct;
    @Basic
    @Column(name = "idProductTheoric")
    private int idProductTheoric;
    @Basic
    @Column(name = "urlImage")
    private String urlImage;

    public int getIdPictureProduct() {
        return idPictureProduct;
    }

    public void setIdPictureProduct(int idPictureProduct) {
        this.idPictureProduct = idPictureProduct;
    }

    public int getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(int idProductTheoric) {
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
