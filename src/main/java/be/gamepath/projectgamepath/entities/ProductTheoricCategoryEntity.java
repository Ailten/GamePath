package com.example.gamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductTheoricCategoryEntity.SelectById",
                query = "select ptc from ProductTheoricCategoryEntity ptc " +
                        "where (ptc.idProductTheoricCategory = :id)"),
        @NamedQuery(name= "ProductTheoricCategoryEntity.SelectMany",
                query = "select ptc from ProductTheoricCategoryEntity ptc "),
})
@Entity
@Table(name = "producttheoriccategory", schema = "gamepath", catalog = "")
public class ProductTheoricCategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProductTheoricCategory")
    private int idProductTheoricCategory;
    @Basic
    @Column(name = "idProductTheoric")
    private int idProductTheoric;
    @Basic
    @Column(name = "idCategory")
    private int idCategory;

    public int getIdProductTheoricCategory() {
        return idProductTheoricCategory;
    }

    public void setIdProductTheoricCategory(int idProductTheoricCategory) {
        this.idProductTheoricCategory = idProductTheoricCategory;
    }

    public int getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(int idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTheoricCategoryEntity that = (ProductTheoricCategoryEntity) o;
        return idProductTheoricCategory == that.idProductTheoricCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductTheoricCategory, idProductTheoric, idCategory);
    }
}
