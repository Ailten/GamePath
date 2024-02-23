package com.example.gamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductTheoricLanguageEntity.SelectById",
                query = "select ptl from ProductTheoricLanguageEntity ptl " +
                        "where (ptl.idProductTheoricLanguage = :id)"),
        @NamedQuery(name= "ProductTheoricLanguageEntity.SelectMany",
                query = "select ptl from ProductTheoricLanguageEntity ptl "),
})
@Entity
@Table(name = "producttheoriclanguage", schema = "gamepath", catalog = "")
public class ProductTheoricLanguageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProductTheoricLanguage")
    private int idProductTheoricLanguage;
    @Basic
    @Column(name = "idProductTheoric")
    private int idProductTheoric;
    @Basic
    @Column(name = "idLanguage")
    private int idLanguage;

    public int getIdProductTheoricLanguage() {
        return idProductTheoricLanguage;
    }

    public void setIdProductTheoricLanguage(int idProductTheoricLanguage) {
        this.idProductTheoricLanguage = idProductTheoricLanguage;
    }

    public int getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(int idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    public int getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(int idLanguage) {
        this.idLanguage = idLanguage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTheoricLanguageEntity that = (ProductTheoricLanguageEntity) o;
        return idProductTheoricLanguage == that.idProductTheoricLanguage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductTheoricLanguage, idProductTheoric, idLanguage);
    }
}
