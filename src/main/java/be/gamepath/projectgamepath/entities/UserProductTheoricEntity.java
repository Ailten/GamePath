package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "UserProductTheoricEntity.SelectById",
                query = "select upt from UserProductTheoricEntity upt " +
                        "where (upt.idUserProductTheoric = :id)"),
        @NamedQuery(name= "UserProductTheoricEntity.SelectMany",
                query = "select upt from UserProductTheoricEntity upt "),
})
@Entity
@Table(name = "userproducttheoric", schema = "gamepath", catalog = "")
public class UserProductTheoricEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idUserProductTheoric", nullable = false)
    private int idUserProductTheoric;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idUser", nullable = false)
    private UserEntity idUser;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoricEntity idProductTheoric;

    @NotNull
    @Column(name = "unlockDate", nullable = false)
    private Timestamp unlockDate;
    @NotNull
    @Pattern(regexp = "^[0-9]{255}$") //TODO: set a valide regex.
    @Column(name = "keyUsed", nullable = false, length = 255)
    private String keyUsed;

    public int getIdUserProductTheoric() {
        return idUserProductTheoric;
    }

    public void setIdUserProductTheoric(int idUserProductTheoric) {
        this.idUserProductTheoric = idUserProductTheoric;
    }

    public UserEntity getIdUser() {
        return idUser;
    }

    public void setIdUser(UserEntity idUser) {
        this.idUser = idUser;
    }

    public ProductTheoricEntity getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(ProductTheoricEntity idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    public Timestamp getUnlockDate() {
        return unlockDate;
    }

    public void setUnlockDate(Timestamp unlockDate) {
        this.unlockDate = unlockDate;
    }

    public String getKeyUsed() {
        return keyUsed;
    }

    public void setKeyUsed(String keyUsed) {
        this.keyUsed = keyUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProductTheoricEntity that = (UserProductTheoricEntity) o;
        return idUserProductTheoric == that.idUserProductTheoric;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserProductTheoric, idUser, idProductTheoric, unlockDate, keyUsed);
    }
}
