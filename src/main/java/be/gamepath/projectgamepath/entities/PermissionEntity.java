package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "PermissionEntity.SelectById",
                query = "select p from PermissionEntity p " +
                        "where (p.idPermission = :id)"),
        @NamedQuery(name= "PermissionEntity.SelectMany",
                query = "select p from PermissionEntity p "),
})
@Entity
@Table(name = "permission", schema = "gamepath", catalog = "")
public class PermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idPermission")
    private int idPermission;
    @Basic
    @Column(name = "title")
    private String title;

    public int getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
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
        PermissionEntity that = (PermissionEntity) o;
        return idPermission == that.idPermission;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPermission, title);
    }
}
