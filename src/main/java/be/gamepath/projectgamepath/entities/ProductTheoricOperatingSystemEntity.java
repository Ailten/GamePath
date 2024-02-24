package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductTheoricOperatingSystemEntity.SelectById",
                query = "select ptos from ProductTheoricOperatingSystemEntity ptos " +
                        "where (ptos.idProductTheoricOperatingSystem = :id)"),
        @NamedQuery(name= "ProductTheoricOperatingSystemEntity.SelectMany",
                query = "select ptos from ProductTheoricOperatingSystemEntity ptos "),
})
@Entity
@Table(name = "producttheoricoperatingsystem", schema = "gamepath", catalog = "")
public class ProductTheoricOperatingSystemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProductTheoricOperatingSystem", nullable = false)
    private int idProductTheoricOperatingSystem;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoricEntity idProductTheoric;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idOperatingSystem", nullable = false)
    private OperatingSystemEntity idOperatingSystem;

    public int getIdProductTheoricOperatingSystem() {
        return idProductTheoricOperatingSystem;
    }

    public void setIdProductTheoricOperatingSystem(int idProductTheoricOperatingSystem) {
        this.idProductTheoricOperatingSystem = idProductTheoricOperatingSystem;
    }

    public ProductTheoricEntity getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(ProductTheoricEntity idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    public OperatingSystemEntity getIdOperatingSystem() {
        return idOperatingSystem;
    }

    public void setIdOperatingSystem(OperatingSystemEntity idOperatingSystem) {
        this.idOperatingSystem = idOperatingSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTheoricOperatingSystemEntity that = (ProductTheoricOperatingSystemEntity) o;
        return idProductTheoricOperatingSystem == that.idProductTheoricOperatingSystem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductTheoricOperatingSystem, idProductTheoric, idOperatingSystem);
    }
}
