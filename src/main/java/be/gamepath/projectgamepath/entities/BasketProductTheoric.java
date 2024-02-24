package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "BasketProductTheoric.SelectById",
                query = "select bp from BasketProductTheoric bp " +
                        "where (bp.id = :id)"),
        @NamedQuery(name= "BasketProductTheoric.SelectMany",
                query = "select bp from BasketProductTheoric bp "),
})
@Entity
@Table(name = "basketproducttheoric", schema = "gamepath", catalog = "")
public class BasketProductTheoric {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idBasketProductTheoric", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idBasket", nullable = false)
    private Basket basket;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoric productTheoric;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public ProductTheoric getProductTheoric() {
        return productTheoric;
    }

    public void setProductTheoric(ProductTheoric productTheoric) {
        this.productTheoric = productTheoric;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketProductTheoric that = (BasketProductTheoric) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, basket, productTheoric);
    }
}
