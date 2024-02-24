package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductTheoricPegi.SelectById",
                query = "select ptp from ProductTheoricPegi ptp " +
                        "where (ptp.id = :id)"),
        @NamedQuery(name= "ProductTheoricPegi.SelectMany",
                query = "select ptp from ProductTheoricPegi ptp "),
})
@Entity
@Table(name = "producttheoricpegi", schema = "gamepath", catalog = "")
public class ProductTheoricPegi {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProductTheoricPegi", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoric productTheoric;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idPegi", nullable = false)
    private Pegi pegi;

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

    public Pegi getPegi() {
        return pegi;
    }

    public void setPegi(Pegi pegi) {
        this.pegi = pegi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTheoricPegi that = (ProductTheoricPegi) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productTheoric, pegi);
    }
}
