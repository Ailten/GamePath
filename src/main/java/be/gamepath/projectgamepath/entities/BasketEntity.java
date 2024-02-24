package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "BasketEntity.SelectById",
                query = "select b from BasketEntity b " +
                        "where (b.idBasket = :id)"),
        @NamedQuery(name= "BasketEntity.SelectMany",
                query = "select b from BasketEntity b "),
})
@Entity
@Table(name = "basket", schema = "gamepath", catalog = "")
public class BasketEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idBasket")
    private int idBasket;
    @Basic
    @Column(name = "idUser")
    private int idUser;

    public int getIdBasket() {
        return idBasket;
    }

    public void setIdBasket(int idBasket) {
        this.idBasket = idBasket;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketEntity that = (BasketEntity) o;
        return idBasket == that.idBasket;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBasket, idUser);
    }
}
