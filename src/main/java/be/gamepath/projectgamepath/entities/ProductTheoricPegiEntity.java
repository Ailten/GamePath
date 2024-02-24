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
    @Column(name = "idProductTheoricPegi")
    private int idProductTheoricPegi;
    @Basic
    @Column(name = "idProductTheoric")
    private int idProductTheoric;
    @Basic
    @Column(name = "idPegi")
    private int idPegi;

    public int getIdProductTheoricPegi() {
        return idProductTheoricPegi;
    }

    public void setIdProductTheoricPegi(int idProductTheoricPegi) {
        this.idProductTheoricPegi = idProductTheoricPegi;
    }

    public int getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(int idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    public int getIdPegi() {
        return idPegi;
    }

    public void setIdPegi(int idPegi) {
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
