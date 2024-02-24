package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.enumeration.PayementType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "Order.SelectById",
                query = "select o from Order o " +
                        "where (o.id = :id)"),
        @NamedQuery(name= "Order.SelectMany",
                query = "select o from Order o "),
})
@Entity
@Table(name = "order", schema = "gamepath", catalog = "")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idOrder", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idUser", nullable = false)
    private User user;
    @NotNull
    @Column(name = "validateBasketDate", nullable = false)
    private Timestamp validateBasketDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payementType", nullable = false)
    private PayementType payementType;

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

    public Timestamp getValidateBasketDate() {
        return validateBasketDate;
    }

    public void setValidateBasketDate(Timestamp validateBasketDate) {
        this.validateBasketDate = validateBasketDate;
    }

    public PayementType getPayementType() {
        return payementType;
    }

    public void setPayementType(PayementType payementType) {
        this.payementType = payementType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order that = (Order) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, validateBasketDate, payementType);
    }
}
