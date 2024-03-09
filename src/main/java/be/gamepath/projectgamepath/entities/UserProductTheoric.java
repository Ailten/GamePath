package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.utility.EntityGenerique;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "UserProductTheoric.SelectById",
                query = "select upt from UserProductTheoric upt " +
                        "where (upt.id = :id)"),
        @NamedQuery(name= "UserProductTheoric.SelectMany",
                query = "select upt from UserProductTheoric upt "),
        @NamedQuery(name= "UserProductTheoric.SelectByBothId",
                query = "select upt from UserProductTheoric upt " +
                        "where (upt.user.id = :idUser and upt.productTheoric.id = :idProductTheoric)"),
        @NamedQuery(name= "UserProductTheoric.SelectManyByFilter",
                query = "select distinct upt from UserProductTheoric upt " +
                        "join ProductTheoric pt on (pt.id = upt.productTheoric.id) " +
                        "join ProductTheoricCategory ptc on (ptc.productTheoric.id = pt.id) " +
                        "join fetch ptc.category c " +
                        "join ProductTheoricPegi ptp on (ptp.productTheoric.id = pt.id) " +
                        "join fetch ptp.pegi p " +
                        "join ProductTheoricLanguage ptl on (ptl.productTheoric.id = pt.id) " +
                        "join fetch ptl.language l " +
                        "join ProductTheoricOperatingSystem ptos on (ptos.productTheoric.id = pt.id) " +
                        "join fetch ptos.operatingSystem os " +
                        "join SocietyProducer sp on (sp.id = pt.societyProducer.id) " +
                        "where (upt.user.id = :idUser and (" +
                        "    (lower(pt.title) like concat('%', :filter, '%')) or " +
                        "    ( lower(c.title) like concat('%', :filter, '%') ) or " +
                        "    ( lower(p.title) like concat('%', :filter, '%') ) or " +
                        "    ( lower(l.title) like concat('%', :filter, '%') ) or " +
                        "    ( lower(os.title) like concat('%', :filter, '%') ) or " +
                        "    ( lower(sp.title) like concat('%', :filter, '%') ) " +
                        "))"),
})
@Entity
@Table(name = "userproducttheoric", schema = "gamepath", catalog = "")
public class UserProductTheoric extends EntityGenerique {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idUserProductTheoric", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idUser", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoric productTheoric;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "unlockDate", nullable = false)
    private Date unlockDate;
    //@NotNull
    @Size(min = 20, max = 255)
    @Pattern(regexp = "^[A-V0-9]{6,}-[A-V0-9]{6,}-[A-V0-9]{6,}$")
    @Column(name = "keyUsed", nullable = false, length = 255)
    private String keyUsed;

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

    public ProductTheoric getProductTheoric() {
        return productTheoric;
    }

    public void setProductTheoric(ProductTheoric productTheoric) {
        this.productTheoric = productTheoric;
    }

    public Date getUnlockDate() {
        return unlockDate;
    }

    public void setUnlockDate(Date unlockDate) {
        this.unlockDate = unlockDate;
    }

    public String getKeyUsed() {
        return keyUsed;
    }

    public void setKeyUsed(String keyUsed) {
        this.keyUsed = keyUsed;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProductTheoric that = (UserProductTheoric) o;
        return id == that.id;
    }
    */

    @Override
    public int hashCode() {
        return Objects.hash(id, user, productTheoric, unlockDate, keyUsed);
    }
}
