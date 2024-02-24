package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "LanguageEntity.SelectById",
                query = "select l from LanguageEntity l " +
                        "where (l.idLanguage = :id)"),
        @NamedQuery(name= "LanguageEntity.SelectMany",
                query = "select l from LanguageEntity l "),
})
@Entity
@Table(name = "language", schema = "gamepath", catalog = "")
public class LanguageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idLanguage")
    private int idLanguage;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "urlImage")
    private String urlImage;

    public int getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(int idLanguage) {
        this.idLanguage = idLanguage;
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
        LanguageEntity that = (LanguageEntity) o;
        return idLanguage == that.idLanguage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLanguage, title, urlImage);
    }
}
