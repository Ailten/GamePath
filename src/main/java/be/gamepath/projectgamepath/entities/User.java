package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.managedBeans.ConnectionBean;
import be.gamepath.projectgamepath.utility.EntityGenerique;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "User.SelectById",
                query = "select u from User u " +
                        "where (u.id = :id)"),
        @NamedQuery(name= "User.SelectMany",
                query = "select u from User u "),
        @NamedQuery(name= "User.SelectRolePermissionOfUser",
                query = "select rp from User as u " +
                        "join Role as r on (u.role.id = r.id) " +
                        "join RolePermission as rp on (r.id = rp.role.id)" +
                        "where (u.id = :idUser)"),
        @NamedQuery(name= "User.SelectUserByLogin",
                query = "select u from User u " +
                        "where (u.email = :loginUser)")
})
@Entity
@Table(name = "user", schema = "gamepath", catalog = "")
public class User extends EntityGenerique {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idUser", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idRole", nullable = false)
    private Role role;
    @NotNull
    @Size(min = 3, max = 255)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") //source : https://regexr.com/3e48o
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @NotNull
    @Size(max = 255)
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @NotNull
    @Size(min = 3, max = 60)
    @Pattern(regexp = "^[a-zA-Z -]{3,60}$")
    @Column(name = "lastName", nullable = false, length = 60)
    private String lastName;
    @NotNull
    @Size(min = 3, max = 60)
    @Pattern(regexp = "^[a-zA-Z -]{3,60}$")
    @Column(name = "firstName", nullable = false, length = 60)
    private String firstName;
    @NotNull
    @Size(max = 45)
    //@Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$") //source : https://ihateregex.io/expr/phone/
    @Pattern(regexp = "^[+][0-9]{1,4}[ ]{1}[0-9]{2,4}[ ]{1}[0-9]{2}[ ]{1}[0-9]{2}[ ]{1}[0-9]{2}$")
    @Column(name = "phone", nullable = false, length = 45)
    private String phone;
    @NotNull
    @Column(name = "birthDate", nullable = false)
    private Date birthDate;
    @NotNull
    @Column(name = "registrationDate", nullable = false)
    private Date registrationDate;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return id == that.id;
    }
    */

    @Override
    public int hashCode() {
        return Objects.hash(id, role, email, password, lastName, firstName, phone, birthDate, registrationDate, isActive);
    }





    @Transient
    public List<RolePermission> listRolePermission;

    @Transient
    public List<RolePermission> getListRolePermission() {
        if (this.listRolePermission == null)
            ConnectionBean.initListRolePermission(this);
        return this.listRolePermission;
    }

    /**
     * Method to verify user access
     * @param permissionTitle title of a permission ask.
     * @return true if the user has permission send.
     */
    @Transient
    public boolean verifyPermission(String permissionTitle)
    {
        return this.getListRolePermission().stream()
                .filter(rp -> rp.getPermission().getTitle().equals(permissionTitle))
                .findFirst()
                .orElse(null) != null;
    }

}
