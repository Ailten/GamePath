package com.example.gamepath.entities;

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
    @Column(name = "idBasketProductTheoric")
    private int idBasketProductTheoric;
    @Basic
    @Column(name = "idBasket")
    private int idBasket;
    @Basic
    @Column(name = "idProductTheoric")
    private int idProductTheoric;

    public int getIdBasketProductTheoric() {
        return idBasketProductTheoric;
    }

    public void setIdBasketProductTheoric(int idBasketProductTheoric) {
        this.idBasketProductTheoric = idBasketProductTheoric;
    }

    public int getIdBasket() {
        return idBasket;
    }

    public void setIdBasket(int idBasket) {
        this.idBasket = idBasket;
    }

    public int getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(int idProductTheoric) {
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
