package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.enumeration.PayementType;
import be.gamepath.projectgamepath.managedBeans.OrderBean;
import be.gamepath.projectgamepath.managedBeans.ProductTheoricBean;
import be.gamepath.projectgamepath.utility.EntityGenerique;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "Order.SelectById",
                query = "select o from Order o " +
                        "where (o.id = :id)"),
        @NamedQuery(name= "Order.SelectMany",
                query = "select o from Order o "),
        @NamedQuery(name= "Order.SelectManyByFilter",
                query = "select distinct o from Order o " +
                        "join fetch o.user u " +
                        "join ProductKey pk on (pk.order.id = o.id) " +
                        "join fetch pk.productTheoric pt " +
                        "where ( " +
                        "  ( " +
                        "    lower(pt.title) like concat('%', :filter, '%') and " +
                        "    (:filterDate is null or (o.validateBasketDate >= :filterDate and o.validateBasketDate < :filterDateNextMonth)) " +
                        "  ) or " +
                        "  ( lower(concat(u.lastName,' ',u.firstName)) like concat('%', :filter, '%') ) " +
                        ")"),
        @NamedQuery(name= "Order.SelectManyByFilterOneUser",
                query = "select distinct o from Order o " +
                        "join fetch o.user u " +
                        "join ProductKey pk on (pk.order.id = o.id) " +
                        "join fetch pk.productTheoric pt " +
                        "where ( u.id = :idUser and ( " +
                        "  ( " +
                        "    lower(pt.title) like concat('%', :filter, '%') and " +
                        "    (:filterDate is null or (o.validateBasketDate >= :filterDate and o.validateBasketDate < :filterDateNextMonth)) " +
                        "  ) " +
                        "))"),
        @NamedQuery(name= "Order.SelectBestSellAnalytics",
                query = "select " +
                        "  pt.title as label, " +
                        "  count(pk.id) as quantity " +
                        "from ProductKey pk " +
                        "join fetch pk.productTheoric pt " +
                        "join fetch pk.order o " +
                        "where ( " +
                        "  (:filterDate is null or (o.validateBasketDate >= :filterDate and o.validateBasketDate < :filterDateNextMonth)) " +
                        ") group by pt.title " +
                        "order by count(pk.id)"),
        @NamedQuery(name= "Order.SelectStatsOfMonthAnalyticsData",
                query = "select " +
                        "  function('date_format', o.validateBasketDate, '%d/%m/%Y') as label, " +
                        "  count(pk.id) as quantity " +
                        "from ProductKey pk " +
                        "join fetch pk.productTheoric pt " +
                        "join fetch pk.order o " +
                        "where ( " +
                        "  (o.validateBasketDate >= :filterDate and o.validateBasketDate < :filterDateNextMonth) " +
                        ") group by (" + //o.validateBasketDate
                        "  function('date_format', o.validateBasketDate, '%d/%m/%Y')" +
                        ") " +
                        "order by function('date_format', o.validateBasketDate, '%d')"),
})
@Entity
@Table(name = "order", schema = "gamepath", catalog = "")
public class Order extends EntityGenerique {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idOrder", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idUser", nullable = false)
    private User user;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "validateBasketDate", nullable = false)
    private Date validateBasketDate; //Timestamp

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

    public Date getValidateBasketDate() {
        return validateBasketDate;
    }

    public void setValidateBasketDate(Date validateBasketDate) {
        this.validateBasketDate = validateBasketDate;
    }

    public PayementType getPayementType() {
        return payementType;
    }

    public void setPayementType(PayementType payementType) {
        this.payementType = payementType;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order that = (Order) o;
        return id == that.id;
    }
    */

    @Override
    public int hashCode() {
        return Objects.hash(id, user, validateBasketDate, payementType);
    }


    @Transient
    private List<ProductKey> listProductKey;
    public List<ProductKey> getListProductKey() {
        if (this.listProductKey == null)
            OrderBean.initListProductKey(this);
        return this.listProductKey;
    }
    public void setListProductKey(List<ProductKey> listProductKey) {
        this.listProductKey = listProductKey;
    }



    @Transient
    private boolean isForMe = true;
    public boolean getIsForMe(){
        return this.isForMe;
    }
    public void setIsForMe(boolean isForMe){ this.isForMe = isForMe; }



    public float getTotalPrice() {
        return this.getListProductKey().stream() //sum of order.
                .map(ProductKey::getPriceWithReduction)
                .reduce(0f, Float::sum);
    }


    public String getUserFullName() {
        return this.getUser().getLastName()+" "+this.getUser().getFirstName();
    }
}
