package com.example.gamepath.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "UserProductTheoricEntity.SelectById",
                query = "select upt from UserProductTheoricEntity upt " +
                        "where (upt.idUserProductTheoric = :id)"),
        @NamedQuery(name= "UserProductTheoricEntity.SelectMany",
                query = "select upt from UserProductTheoricEntity upt "),
})
@Entity
@Table(name = "userproducttheoric", schema = "gamepath", catalog = "")
public class UserProductTheoricEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idUserProductTheoric")
    private int idUserProductTheoric;
    @Basic
    @Column(name = "idUser")
    private int idUser;
    @Basic
    @Column(name = "idProductTheoric")
    private int idProductTheoric;
    @Basic
    @Column(name = "unlockDate")
    private Timestamp unlockDate;
    @Basic
    @Column(name = "keyUsed")
    private String keyUsed;

    public int getIdUserProductTheoric() {
        return idUserProductTheoric;
    }

    public void setIdUserProductTheoric(int idUserProductTheoric) {
        this.idUserProductTheoric = idUserProductTheoric;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(int idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    public Timestamp getUnlockDate() {
        return unlockDate;
    }

    public void setUnlockDate(Timestamp unlockDate) {
        this.unlockDate = unlockDate;
    }

    public String getKeyUsed() {
        return keyUsed;
    }

    public void setKeyUsed(String keyUsed) {
        this.keyUsed = keyUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProductTheoricEntity that = (UserProductTheoricEntity) o;
        return idUserProductTheoric == that.idUserProductTheoric;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserProductTheoric, idUser, idProductTheoric, unlockDate, keyUsed);
    }
}
