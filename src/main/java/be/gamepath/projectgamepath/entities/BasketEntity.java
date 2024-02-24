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
    @Column(name = "idBasket", nullable = false)
    private int idBasket;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idUser", nullable = false)
    private UserEntity idUser;

    public int getIdBasket() {
        return idBasket;
    }

    public void setIdBasket(int idBasket) {
        this.idBasket = idBasket;
    }

    public UserEntity getIdUser() {
        return idUser;
    }

    public void setIdUser(UserEntity idUser) {
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
