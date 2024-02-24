package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
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
    @Column(name = "idOperatingSystem")
    private int idOperatingSystem;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "urlImage")
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
