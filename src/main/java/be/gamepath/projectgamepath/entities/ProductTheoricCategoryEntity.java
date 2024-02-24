package be.gamepath.projectgamepath.entities;

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
    @Column(name = "idProductTheoricCategory", nullable = false)
    private int idProductTheoricCategory;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoricEntity idProductTheoric;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idCategory", nullable = false)
    private CategoryEntity idCategory;

    public int getIdProductTheoricCategory() {
        return idProductTheoricCategory;
    }

    public void setIdProductTheoricCategory(int idProductTheoricCategory) {
        this.idProductTheoricCategory = idProductTheoricCategory;
    }

    public ProductTheoricEntity getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(ProductTheoricEntity idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    public CategoryEntity getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(CategoryEntity idCategory) {
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
