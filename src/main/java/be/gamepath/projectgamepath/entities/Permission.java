package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "Permission.SelectById",
                query = "select p from Permission p " +
                        "where (p.id = :id)"),
        @NamedQuery(name= "Permission.SelectMany",
                query = "select p from Permission p "),
})
@Entity
@Table(name = "permission", schema = "gamepath", catalog = "")
public class Permission {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idPermission", nullable = false)
    private int id;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z -]{3,60}$")
    @Column(name = "title", nullable = false, length = 60)
    private String title;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}