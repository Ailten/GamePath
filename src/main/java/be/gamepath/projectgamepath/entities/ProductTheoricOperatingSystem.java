package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.utility.EntityGenerique;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductTheoricOperatingSystem.SelectById",
                query = "select ptos from ProductTheoricOperatingSystem ptos " +
                        "where (ptos.id = :id)"),
        @NamedQuery(name= "ProductTheoricOperatingSystem.SelectMany",
                query = "select ptos from ProductTheoricOperatingSystem ptos "),
})
@Entity
@Table(name = "producttheoricoperatingsystem", schema = "gamepath", catalog = "")
public class ProductTheoricOperatingSystem extends EntityGenerique {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProductTheoricOperatingSystem", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoric productTheoric;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idOperatingSystem", nullable = false)
    private OperatingSystem operatingSystem;

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

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTheoricOperatingSystem that = (ProductTheoricOperatingSystem) o;
        return id == that.id;
    }
    */

    @Override
    public int hashCode() {
        return Objects.hash(id, productTheoric, operatingSystem);
    }
}
