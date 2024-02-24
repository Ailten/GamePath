package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "Language.SelectById",
                query = "select l from Language l " +
                        "where (l.id = :id)"),
        @NamedQuery(name= "Language.SelectMany",
                query = "select l from Language l "),
})
@Entity
@Table(name = "language", schema = "gamepath", catalog = "")
public class Language {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idLanguage", nullable = false)
    private int id;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z -]{3,60}$")
    @Column(name = "title", nullable = false, length = 60)
    private String title;
    @NotNull
    @Pattern(regexp = "^http://") //canvas : http://localhost/imageFolderLocalHost/******.png
    @Column(name = "urlImage", nullable = false, length = 255)
    private String urlImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        Language that = (Language) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, urlImage);
    }
}