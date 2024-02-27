package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.enumeration.Tva;
import be.gamepath.projectgamepath.utility.EntityGenerique;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductKey.SelectById",
                query = "select pk from ProductKey pk " +
                        "where (pk.id = :id)"),
        @NamedQuery(name= "ProductKey.SelectMany",
                query = "select pk from ProductKey pk "),
})
@Entity
@Table(name = "productkey", schema = "gamepath", catalog = "")
public class ProductKey extends EntityGenerique {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProductKey", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idOrder", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoric productTheoric;
    @NotNull
    @Pattern(regexp = "^[0-9]{255}$") //TODO: set a valide regex.
    @Column(name = "key", nullable = false, length = 255)
    private String key;

    @NotNull
    @Min(1)
    @Column(name = "currentPriceHTVA", nullable = false)
    private float currentPriceHtva;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currentTVA", nullable = false)
    private Tva currentTva;
    @NotNull
    @Min(0)
    @Max(90)
    @Column(name = "currentReduction", nullable = false)
    private int currentReduction;

    @Column(name = "isValid", nullable = false)
    private byte isValid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductTheoric getProductTheoric() {
        return productTheoric;
    }

    public void setProductTheoric(ProductTheoric productTheoric) {
        this.productTheoric = productTheoric;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public float getCurrentPriceHtva() {
        return currentPriceHtva;
    }

    public void setCurrentPriceHtva(float currentPriceHtva) {
        this.currentPriceHtva = currentPriceHtva;
    }

    public Tva getCurrentTva() {
        return currentTva;
    }

    public void setCurrentTva(Tva currentTva) {
        this.currentTva = currentTva;
    }

    public int getCurrentReduction() {
        return currentReduction;
    }

    public void setCurrentReduction(int currentReduction) {
        this.currentReduction = currentReduction;
    }

    public byte getIsValid() {
        return isValid;
    }

    public void setIsValid(byte isValid) {
        this.isValid = isValid;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductKey that = (ProductKey) o;
        return id == that.id;
    }
    */

    @Override
    public int hashCode() {
        return Objects.hash(id, order, productTheoric, key, currentPriceHtva, currentTva, currentReduction, isValid);
    }
}
