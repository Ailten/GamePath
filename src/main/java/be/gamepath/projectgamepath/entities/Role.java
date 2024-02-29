package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.managedBeans.RoleBean;
import be.gamepath.projectgamepath.utility.EntityGenerique;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "Role.SelectById",
                query = "select r from Role r " +
                        "where (r.id = :id)"),
        @NamedQuery(name= "Role.SelectMany",
                query = "select r from Role r "),
})
@Entity
@Table(name = "role", schema = "gamepath", catalog = "")
public class Role extends EntityGenerique {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idRole", nullable = false)
    private int id;

    @NotNull
    @Size(min = 3, max = 60)
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

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role that = (Role) o;
        return id == that.id;
    }
    */

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }



    @Transient
    private List<Permission> listPermission;
    public List<Permission> getListPermission() {
        if (this.listPermission == null)
            RoleBean.initListPermission(this);
        return this.listPermission;
    }
    public void setListPermission(List<Permission> listPermission) {
        this.listPermission = listPermission;
    }


    /**
     * Method to verify role has permission
     * @param permissionTitle title of a permission ask.
     * @return true if the role has permission send.
     */
    public boolean verifyPermission(String permissionTitle)
    {
        return this.getListPermission().stream()
                .filter(p -> p.getTitle().equals(permissionTitle))
                .findFirst()
                .orElse(null) != null;
    }
}
