package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.enumeration.Tva;
import be.gamepath.projectgamepath.utility.EntityGenerique;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductKey.SelectById",
                query = "select pk from ProductKey pk " +
                        "where (pk.id = :id)"),
        @NamedQuery(name= "ProductKey.SelectMany",
                query = "select pk from ProductKey pk "),
        @NamedQuery(name= "ProductKey.SelectManyByIdOrder",
                query = "select pk from ProductKey pk " +
                        "join fetch pk.order o " +
                        "where o.id = :idOrder"),
        @NamedQuery(name= "ProductKey.SelectByKeyCode",
                query = "select pk from ProductKey pk " +
                        "where pk.key = :key"),
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
    @Size(min = 20, max = 255)
    @Pattern(regexp = "^[A-V0-9]{6,}-[A-V0-9]{6,}-[A-V0-9]{6,}$")
    @Column(name = "keyCode", nullable = false, length = 255)
    private String key;

    @NotNull
    @Min(0)
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
    private boolean isValid;

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

    public boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
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


    //generate key.
    public void generateKey() throws Exception {
        if((this.order == null || this.order.getId() == 0) ||
            this.getId() == 0 ||
            (this.productTheoric == null || this.productTheoric.getId() == 0)
        ){
            throw new Exception("error can't generate key with every id.");
        }

        StringBuilder orderNum = new StringBuilder(Integer.toString(this.order.getId(), 32));
        StringBuilder keyNum = new StringBuilder(Integer.toString(this.getId(), 32));
        StringBuilder productNum = new StringBuilder(Integer.toString(this.productTheoric.getId(), 32));

        while(orderNum.length() < 6)
            orderNum.insert(0, "0");
        while(keyNum.length() < 6)
            keyNum.insert(0, "0");
        while(productNum.length() < 6)
            productNum.insert(0, "0");

        this.key = (orderNum.toString()+"-"+keyNum.toString()+"-"+productNum.toString()).toUpperCase();
    }



    public float getPrice(){
        if(this.currentTva == null)
            return this.currentPriceHtva;
        return this.currentPriceHtva + this.currentTva.evalTva(this.currentPriceHtva);
    }
    public float getPriceWithReduction(){
        return this.getPrice() * (1 - ((float)this.currentReduction)/100);
    }


    public boolean hasReduction(){
        return this.getCurrentReduction() != 0;
    }
}
