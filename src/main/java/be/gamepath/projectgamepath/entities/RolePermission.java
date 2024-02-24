package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "RolePermission.SelectById",
                query = "select rp from RolePermission rp " +
                        "where (rp.id = :id)"),
        @NamedQuery(name= "RolePermission.SelectMany",
                query = "select rp from RolePermission rp "),
})
@Entity
@Table(name = "rolepermission", schema = "gamepath", catalog = "")
public class RolePermission {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idRolePermission", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idRole", nullable = false)
    private Role role;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idPermission", nullable = false)
    private Permission permission;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission that = (RolePermission) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, permission);
    }
}
