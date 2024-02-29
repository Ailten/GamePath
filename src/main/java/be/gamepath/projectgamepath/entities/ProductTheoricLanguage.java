package be.gamepath.projectgamepath.entities;

import be.gamepath.projectgamepath.utility.EntityGenerique;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductTheoricLanguage.SelectById",
                query = "select ptl from ProductTheoricLanguage ptl " +
                        "where (ptl.id = :id)"),
        @NamedQuery(name= "ProductTheoricLanguage.SelectMany",
                query = "select ptl from ProductTheoricLanguage ptl "),
        @NamedQuery(name= "ProductTheoricLanguage.SelectByBothId",
                query = "select ptl from ProductTheoricLanguage ptl " +
                        "where ( " +
                        "  ptl.productTheoric.id = :idProductTheoric and " +
                        "  ptl.language.id = :idLanguage " +
                        ")"),
})
@Entity
@Table(name = "producttheoriclanguage", schema = "gamepath", catalog = "")
public class ProductTheoricLanguage extends EntityGenerique {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProductTheoricLanguage")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idProductTheoric", nullable = false)
    private ProductTheoric productTheoric;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idLanguage", nullable = false)
    private Language language;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductTheoric getProductTheoric() {
        return productTheoric;
    }

    public void setProductTheoric(ProductTheoric productTheoric) {
        this.productTheoric = productTheoric;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTheoricLanguage that = (ProductTheoricLanguage) o;
        return id == that.id;
    }
    */

    @Override
    public int hashCode() {
        return Objects.hash(id, productTheoric, language);
    }
}
