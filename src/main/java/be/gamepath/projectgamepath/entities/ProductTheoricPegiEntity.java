package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductTheoricPegiEntity.SelectById",
                query = "select ptp from ProductTheoricPegiEntity ptp " +
                        "where (ptp.idProductTheoricPegi = :id)"),
        @NamedQuery(name= "ProductTheoricPegiEntity.SelectMany",
                query = "select ptp from ProductTheoricPegiEntity ptp "),
})
@Entity
@Table(name = "producttheoricpegi", schema = "gamepath", catalog = "")
public class ProductTheoricPegiEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProductTheoricPegi", nullable = false)
    private int idProductTheoricPegi;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoricEntity idProductTheoric;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idPegi", nullable = false)
    private PegiEntity idPegi;

    public int getIdProductTheoricPegi() {
        return idProductTheoricPegi;
    }

    public void setIdProductTheoricPegi(int idProductTheoricPegi) {
        this.idProductTheoricPegi = idProductTheoricPegi;
    }

    public ProductTheoricEntity getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(ProductTheoricEntity idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    public PegiEntity getIdPegi() {
        return idPegi;
    }

    public void setIdPegi(PegiEntity idPegi) {
        this.idPegi = idPegi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTheoricPegiEntity that = (ProductTheoricPegiEntity) o;
        return idProductTheoricPegi == that.idProductTheoricPegi;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductTheoricPegi, idProductTheoric, idPegi);
    }
}
