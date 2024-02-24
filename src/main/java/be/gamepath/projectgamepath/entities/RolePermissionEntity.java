package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "RolePermissionEntity.SelectById",
                query = "select rp from RolePermissionEntity rp " +
                        "where (rp.idRolePermission = :id)"),
        @NamedQuery(name= "RolePermissionEntity.SelectMany",
                query = "select rp from RolePermissionEntity rp "),
})
@Entity
@Table(name = "rolepermission", schema = "gamepath", catalog = "")
public class RolePermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idRolePermission")
    private int idRolePermission;
    @Basic
    @Column(name = "idRole")
    private int idRole;
    @Basic
    @Column(name = "idPermission")
    private int idPermission;

    public int getIdRolePermission() {
        return idRolePermission;
    }

    public void setIdRolePermission(int idRolePermission) {
        this.idRolePermission = idRolePermission;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public int getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermissionEntity that = (RolePermissionEntity) o;
        return idRolePermission == that.idRolePermission;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRolePermission, idRole, idPermission);
    }
}
