package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.utility.EntityGenerique;
import be.gamepath.projectgamepath.utility.FileManaging;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "PictureProduct.SelectById",
                query = "select pp from PictureProduct pp " +
                        "where (pp.id = :id)"),
        @NamedQuery(name= "PictureProduct.SelectMany",
                query = "select pp from PictureProduct pp "),
        @NamedQuery(name= "PictureProduct.SelectManyByIdProduct",
                query = "select pp from PictureProduct pp " +
                        "where (pp.productTheoric.id = :idProductTheoric)"),
})
@Entity
@Table(name = "pictureproduct", schema = "gamepath", catalog = "")
public class PictureProduct extends EntityGenerique {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idPictureProduct", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoric productTheoric;
    @NotNull
    @Size(min = 3, max = 255)
    @Pattern(regexp = "^http://") //canvas : http://localhost/imageFolderLocalHost/******.png
    @Column(name = "urlImage", nullable = false, length = 255)
    private String urlImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductTheoric getProductTheoric() {
        return productTheoric;
    }

    public void setProductTheoric(ProductTheoric productTheoric) {
        this.productTheoric = productTheoric;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureProduct that = (PictureProduct) o;
        return id == that.id;
    }
    */

    @Override
    public int hashCode() {
        return Objects.hash(id, productTheoric, urlImage);
    }


    //function for get full url of image in server.
    public String getUrlImageToDraw(){
        if(this.getUrlImage() == null)
            return FileManaging.getDefaultUrlForApply();
        return FileManaging.getUrlForApply(this.getUrlImage(), "pictureProduct");
    }
}
