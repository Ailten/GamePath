package com.example.gamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "RoleEntity.SelectById",
                query = "select r from RoleEntity r " +
                        "where (r.idRole = :id)"),
        @NamedQuery(name= "RoleEntity.SelectMany",
                query = "select r from RoleEntity r "),
})
@Entity
@Table(name = "role", schema = "gamepath", catalog = "")
public class RoleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idRole")
    private int idRole;
    @Basic
    @Column(name = "title")
    private String title;

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
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
        RoleEntity that = (RoleEntity) o;
        return idRole == that.idRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, title);
    }
}
