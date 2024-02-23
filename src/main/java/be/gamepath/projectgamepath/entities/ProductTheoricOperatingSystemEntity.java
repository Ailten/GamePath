package com.example.gamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "ProductTheoricOperatingSystemEntity.SelectById",
                query = "select ptos from ProductTheoricOperatingSystemEntity ptos " +
                        "where (ptos.idProductTheoricOperatingSystem = :id)"),
        @NamedQuery(name= "ProductTheoricOperatingSystemEntity.SelectMany",
                query = "select ptos from ProductTheoricOperatingSystemEntity ptos "),
})
@Entity
@Table(name = "producttheoricoperatingsystem", schema = "gamepath", catalog = "")
public class ProductTheoricOperatingSystemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProductTheoricOperatingSystem")
    private int idProductTheoricOperatingSystem;
    @Basic
    @Column(name = "idProductTheoric")
    private int idProductTheoric;
    @Basic
    @Column(name = "idOperatingSystem")
    private int idOperatingSystem;

    public int getIdProductTheoricOperatingSystem() {
        return idProductTheoricOperatingSystem;
    }

    public void setIdProductTheoricOperatingSystem(int idProductTheoricOperatingSystem) {
        this.idProductTheoricOperatingSystem = idProductTheoricOperatingSystem;
    }

    public int getIdProductTheoric() {
        return idProductTheoric;
    }

    public void setIdProductTheoric(int idProductTheoric) {
        this.idProductTheoric = idProductTheoric;
    }

    public int getIdOperatingSystem() {
        return idOperatingSystem;
    }

    public void setIdOperatingSystem(int idOperatingSystem) {
        this.idOperatingSystem = idOperatingSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTheoricOperatingSystemEntity that = (ProductTheoricOperatingSystemEntity) o;
        return idProductTheoricOperatingSystem == that.idProductTheoricOperatingSystem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductTheoricOperatingSystem, idProductTheoric, idOperatingSystem);
    }
}
