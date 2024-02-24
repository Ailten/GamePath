package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.managedBeans.ConnectionBean;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "UserEntity.SelectById",
                query = "select u from UserEntity u " +
                        "where (u.idUser = :id)"),
        @NamedQuery(name= "UserEntity.SelectMany",
                query = "select u from UserEntity u "),
        @NamedQuery(name= "UserEntity.SelectRolePermissionOfUser",
                query = "select rp from RoleEntity r " +
                        "join RolePermissionEntity rp on (r.idRole = rp.idRole.idRole) "+
                        "join UserEntity u on (r.idRole = u.idRole.idRole) "+
                        "where (u.idUser = :idUser)"),
})
@Entity
@Table(name = "user", schema = "gamepath", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idUser", nullable = false)
    private int idUser;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idRole", nullable = false)
    private RoleEntity idRole;
    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") //source : https://regexr.com/3e48o
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @NotNull
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z -]{3,60}$")
    @Column(name = "lastName", nullable = false, length = 60)
    private String lastName;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z -]{3,60}$")
    @Column(name = "firstName", nullable = false, length = 60)
    private String firstName;
    @NotNull
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$") //source : https://ihateregex.io/expr/phone/
    //@Pattern(regexp = "^[+][0-9]{1,4}[ ]{1}[0-9]{2,4}[ ]{1}[0-9]{2}[ ]{1}[0-9]{2}[ ]{1}[0-9]{2}$")
    @Column(name = "phone", nullable = false, length = 45)
    private String phone;
    @NotNull
    @Column(name = "birthDate", nullable = false)
    private Date birthDate;
    @NotNull
    @Column(name = "registrationDate", nullable = false)
    private Date registrationDate;

    @Column(name = "isActive", nullable = false)
    private byte isActive;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public RoleEntity getIdRole() {
        return idRole;
    }

    public void setIdRole(RoleEntity idRole) {
        this.idRole = idRole;
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

    public byte getIsActive() {
        return isActive;
    }

    public void setIsActive(byte isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return idUser == that.idUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idRole, email, password, lastName, firstName, phone, birthDate, registrationDate, isActive);
    }





    @Transient
    public List<RolePermissionEntity> listRolePermission;

    @Transient
    public List<RolePermissionEntity> getListRolePermission() {
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
                .filter(rp -> rp.getIdPermission().getTitle().equals(permissionTitle))
                .findFirst()
                .orElse(null) != null;
    }
}
