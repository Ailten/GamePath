package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.enumeration.PayementType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "OrderEntity.SelectById",
                query = "select o from OrderEntity o " +
                        "where (o.idOrder = :id)"),
        @NamedQuery(name= "OrderEntity.SelectMany",
                query = "select o from OrderEntity o "),
})
@Entity
@Table(name = "order", schema = "gamepath", catalog = "")
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idOrder", nullable = false)
    private int idOrder;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idUser", nullable = false)
    private UserEntity idUser;
    @NotNull
    @Column(name = "validateBasketDate", nullable = false)
    private Timestamp validateBasketDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payementType", nullable = false)
    private PayementType payementType;

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public UserEntity getIdUser() {
        return idUser;
    }

    public void setIdUser(UserEntity idUser) {
        this.idUser = idUser;
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
        OrderEntity that = (OrderEntity) o;
        return idOrder == that.idOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, idUser, validateBasketDate, payementType);
    }
}
