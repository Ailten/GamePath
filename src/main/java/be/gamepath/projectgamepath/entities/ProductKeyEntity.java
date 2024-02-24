package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.enumeration.Tva;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductKeyEntity.SelectById",
                query = "select pk from ProductKeyEntity pk " +
                        "where (pk.idProductKey = :id)"),
        @NamedQuery(name= "ProductKeyEntity.SelectMany",
                query = "select pk from ProductKeyEntity pk "),
})
@Entity
@Table(name = "productkey", schema = "gamepath", catalog = "")
public class ProductKeyEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProductKey", nullable = false)
    private int idProductKey;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idOrder", nullable = false)
    private OrderEntity idOrder;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoricEntity idProductTheoric;
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

    public int getIdProductKey() {
        return idProductKey;
    }

    public void setIdProductKey(int idProductKey) {
        this.idProductKey = idProductKey;
    }

    public OrderEntity getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(OrderEntity idOrder) {
        this.idOrder = idOrder;
    }

    public ProductTheoricEntity getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(ProductTheoricEntity idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductKeyEntity that = (ProductKeyEntity) o;
        return idProductKey == that.idProductKey;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductKey, idOrder, idProductTheoric, key, currentPriceHtva, currentTva, currentReduction, isValid);
    }
}
