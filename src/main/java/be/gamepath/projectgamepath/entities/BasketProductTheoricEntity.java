package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "BasketProductTheoricEntity.SelectById",
                query = "select bp from BasketProductTheoricEntity bp " +
                        "where (bp.idBasketProductTheoric = :id)"),
        @NamedQuery(name= "BasketProductTheoricEntity.SelectMany",
                query = "select bp from BasketProductTheoricEntity bp "),
})
@Entity
@Table(name = "basketproducttheoric", schema = "gamepath", catalog = "")
public class BasketProductTheoricEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idBasketProductTheoric", nullable = false)
    private int idBasketProductTheoric;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idBasket", nullable = false)
    private BasketEntity idBasket;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoricEntity idProductTheoric;

    public int getIdBasketProductTheoric() {
        return idBasketProductTheoric;
    }

    public void setIdBasketProductTheoric(int idBasketProductTheoric) {
        this.idBasketProductTheoric = idBasketProductTheoric;
    }

    public BasketEntity getIdBasket() {
        return idBasket;
    }

    public void setIdBasket(BasketEntity idBasket) {
        this.idBasket = idBasket;
    }

    public ProductTheoricEntity getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(ProductTheoricEntity idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketProductTheoricEntity that = (BasketProductTheoricEntity) o;
        return idBasketProductTheoric == that.idBasketProductTheoric;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBasketProductTheoric, idBasket, idProductTheoric);
    }
}
