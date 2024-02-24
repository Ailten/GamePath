package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "Basket.SelectById",
                query = "select b from Basket b " +
                        "where (b.id = :id)"),
        @NamedQuery(name= "Basket.SelectMany",
                query = "select b from Basket b "),
})
@Entity
@Table(name = "basket", schema = "gamepath", catalog = "")
public class Basket {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket that = (Basket) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }
}
