package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.enumeration.PayementType;
import be.gamepath.projectgamepath.managedBeans.BasketBean;
import be.gamepath.projectgamepath.managedBeans.ProductTheoricBean;
import be.gamepath.projectgamepath.utility.EntityGenerique;
import be.gamepath.projectgamepath.utility.Utility;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "Basket.SelectById",
                query = "select b from Basket b " +
                        "where (b.id = :id)"),
        @NamedQuery(name= "Basket.SelectMany",
                query = "select b from Basket b "),
        @NamedQuery(name= "Basket.SelectByIdUser",
                query = "select b from Basket b " +
                        "join User u on (b.user.id = u.id) " +
                        "where (u.id = :idUser)"),
})
@Entity
@Table(name = "basket", schema = "gamepath", catalog = "")
public class Basket extends EntityGenerique {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idBasket", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket that = (Basket) o;
        return id == that.id;
    }
    */

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }


    @Transient
    private List<ProductTheoric> listProductTheoric;
    public List<ProductTheoric> getListProductTheoric() {
        if (this.listProductTheoric == null)
            BasketBean.initListProductTheoric(this);
        return this.listProductTheoric;
    }
    public void setListProductTheoric(List<ProductTheoric> listProductTheoric) {
        this.listProductTheoric = listProductTheoric;
    }


    //return the sum of price with reduction of all product in basket.
    public float getFullPrice(){
        return this.getListProductTheoric().stream()
                .map(ProductTheoric::getPriceWithReduction)
                .reduce(0f, Float::sum);
    }
    public void setFullPrice(Float fullPrice){
        /* for interact with input front */
    }


    @Transient
    @NotNull
    private PayementType payementType = PayementType.MASTERCARD;
    public PayementType getPayementType(){
        return this.payementType;
    }
    public void setPayementType(PayementType payementType){
        this.payementType = payementType;
    }



    @Transient
    private boolean isForMe = true;
    public boolean getIsForMe(){
        return this.isForMe;
    }
    public void setIsForMe(boolean isForMe){ this.isForMe = isForMe; }

}
