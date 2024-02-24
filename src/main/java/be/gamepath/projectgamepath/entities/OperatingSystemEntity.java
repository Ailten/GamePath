package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "OperatingSystemEntity.SelectById",
                query = "select os from OperatingSystemEntity os " +
                        "where (os.idOperatingSystem = :id)"),
        @NamedQuery(name= "OperatingSystemEntity.SelectMany",
                query = "select os from OperatingSystemEntity os "),
})
@Entity
@Table(name = "operatingsystem", schema = "gamepath", catalog = "")
public class OperatingSystemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idOperatingSystem", nullable = false)
    private int idOperatingSystem;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z -]{3,60}$")
    @Column(name = "title", nullable = false, length = 60)
    private String title;
    @NotNull
    @Pattern(regexp = "^http://") //canvas : http://localhost/imageFolderLocalHost/******.png
    @Column(name = "urlImage", nullable = false, length = 255)
    private String urlImage;

    public int getIdOperatingSystem() {
        return idOperatingSystem;
    }

    public void setIdOperatingSystem(int idOperatingSystem) {
        this.idOperatingSystem = idOperatingSystem;
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
        OperatingSystemEntity that = (OperatingSystemEntity) o;
        return idOperatingSystem == that.idOperatingSystem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOperatingSystem, title, urlImage);
    }
}
