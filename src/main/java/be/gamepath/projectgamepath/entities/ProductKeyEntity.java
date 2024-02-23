package com.example.gamepath.entities;

import javax.persistence.*;
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
    @Column(name = "idProductKey")
    private int idProductKey;
    @Basic
    @Column(name = "idOrder")
    private int idOrder;
    @Basic
    @Column(name = "idProductTheoric")
    private int idProductTheoric;
    @Basic
    @Column(name = "key")
    private String key;
    @Basic
    @Column(name = "currentPriceHTVA")
    private double currentPriceHtva;
    @Basic
    @Column(name = "currentTVA")
    private Object currentTva;
    @Basic
    @Column(name = "currentReduction")
    private int currentReduction;
    @Basic
    @Column(name = "isValid")
    private byte isValid;

    public int getIdProductKey() {
        return idProductKey;
    }

    public void setIdProductKey(int idProductKey) {
        this.idProductKey = idProductKey;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(int idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getCurrentPriceHtva() {
        return currentPriceHtva;
    }

    public void setCurrentPriceHtva(double currentPriceHtva) {
        this.currentPriceHtva = currentPriceHtva;
    }

    public Object getCurrentTva() {
        return currentTva;
    }

    public void setCurrentTva(Object currentTva) {
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
