package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "SocietyProducerEntity.SelectById",
                query = "select sp from SocietyProducerEntity sp " +
                        "where (sp.idSocietyProducer = :id)"),
        @NamedQuery(name= "SocietyProducerEntity.SelectMany",
                query = "select sp from SocietyProducerEntity sp "),
})
@Entity
@Table(name = "societyproducer", schema = "gamepath", catalog = "")
public class SocietyProducerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idSocietyProducer")
    private int idSocietyProducer;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "numeroTVA")
    private int numeroTva;

    public int getIdSocietyProducer() {
        return idSocietyProducer;
    }

    public void setIdSocietyProducer(int idSocietyProducer) {
        this.idSocietyProducer = idSocietyProducer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNumeroTva() {
        return numeroTva;
    }

    public void setNumeroTva(int numeroTva) {
        this.numeroTva = numeroTva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocietyProducerEntity that = (SocietyProducerEntity) o;
        return idSocietyProducer == that.idSocietyProducer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSocietyProducer, title, email, phone, numeroTva);
    }
}
