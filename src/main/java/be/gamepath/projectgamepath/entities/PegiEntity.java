package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "PegiEntity.SelectById",
                query = "select p from PegiEntity p " +
                        "where (p.idPegi = :id)"),
        @NamedQuery(name= "PegiEntity.SelectMany",
                query = "select p from PegiEntity p "),
})
@Entity
@Table(name = "pegi", schema = "gamepath", catalog = "")
public class PegiEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idPegi")
    private int idPegi;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "urlImage")
    private String urlImage;

    public int getIdPegi() {
        return idPegi;
    }

    public void setIdPegi(int idPegi) {
        this.idPegi = idPegi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        PegiEntity that = (PegiEntity) o;
        return idPegi == that.idPegi;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPegi, title, urlImage);
    }
}
