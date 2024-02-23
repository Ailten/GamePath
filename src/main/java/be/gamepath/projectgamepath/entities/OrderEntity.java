package com.example.gamepath.entities;

import javax.persistence.*;
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
    @Column(name = "idOrder")
    private int idOrder;
    @Basic
    @Column(name = "idUser")
    private int idUser;
    @Basic
    @Column(name = "validateBasketDate")
    private Timestamp validateBasketDate;
    @Basic
    @Column(name = "payementType")
    private Object payementType;

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Timestamp getValidateBasketDate() {
        return validateBasketDate;
    }

    public void setValidateBasketDate(Timestamp validateBasketDate) {
        this.validateBasketDate = validateBasketDate;
    }

    public Object getPayementType() {
        return payementType;
    }

    public void setPayementType(Object payementType) {
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
