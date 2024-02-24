package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductTheoricCategory.SelectById",
                query = "select ptc from ProductTheoricCategory ptc " +
                        "where (ptc.id = :id)"),
        @NamedQuery(name= "ProductTheoricCategory.SelectMany",
                query = "select ptc from ProductTheoricCategory ptc "),
})
@Entity
@Table(name = "producttheoriccategory", schema = "gamepath", catalog = "")
public class ProductTheoricCategory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProductTheoricCategory", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoric productTheoric;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idCategory", nullable = false)
    private Category category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductTheoric getProductTheoric() {
        return productTheoric;
    }

    public void setProductTheoric(ProductTheoric productTheoric) {
        this.productTheoric = productTheoric;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTheoricCategory that = (ProductTheoricCategory) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productTheoric, category);
    }
}
