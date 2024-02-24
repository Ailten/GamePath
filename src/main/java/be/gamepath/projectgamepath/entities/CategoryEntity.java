package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "CategoryEntity.SelectById",
                query = "select c from CategoryEntity c " +
                        "where (c.idCategory = :id)"),
        @NamedQuery(name= "CategoryEntity.SelectMany",
                query = "select c from CategoryEntity c "),
})
@Entity
@Table(name = "category", schema = "gamepath", catalog = "")
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idCategory", nullable = false)
    private int idCategory;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z -]{3,60}$")
    @Column(name = "title", nullable = false, length = 60)
    private String title;

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return idCategory == that.idCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategory, title);
    }
}
