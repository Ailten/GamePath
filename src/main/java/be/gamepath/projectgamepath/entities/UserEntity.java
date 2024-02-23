package com.example.gamepath.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "UserEntity.SelectById",
                query = "select u from UserEntity u " +
                        "where (u.idUser = :id)"),
        @NamedQuery(name= "UserEntity.SelectMany",
                query = "select u from UserEntity u "),
})
@Entity
@Table(name = "user", schema = "gamepath", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idUser")
    private int idUser;
    @Basic
    @Column(name = "idRole")
    private int idRole;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "lastName")
    private String lastName;
    @Basic
    @Column(name = "firstName")
    private String firstName;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "birthDate")
    private Date birthDate;
    @Basic
    @Column(name = "registrationDate")
    private Date registrationDate;
    @Basic
    @Column(name = "isActive")
    private byte isActive;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
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
}
