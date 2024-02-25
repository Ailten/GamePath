package be.gamepath.projectgamepath.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name= "SocietyProducer.SelectById",
                query = "select sp from SocietyProducer sp " +
                        "where (sp.id = :id)"),
        @NamedQuery(name= "SocietyProducer.SelectMany",
                query = "select sp from SocietyProducer sp "),
})
@Entity
@Table(name = "societyproducer", schema = "gamepath", catalog = "")
public class SocietyProducer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idSocietyProducer", nullable = false)
    private int id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z -]{3,60}$")
    @Column(name = "title", nullable = false, length = 60)
    private String title;
    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") //source : https://regexr.com/3e48o
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @NotNull
    //@Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$") //source : https://ihateregex.io/expr/phone/
    @Pattern(regexp = "^[+][0-9]{1,4}[ ]{1}[0-9]{2,4}[ ]{1}[0-9]{2}[ ]{1}[0-9]{2}[ ]{1}[0-9]{2}$")
    @Column(name = "phone", nullable = false, length = 45)
    private String phone;
    @NotNull
    //@Pattern(regexp = "^[A-Z0-9.]{8,11}$")
    @Column(name = "numeroTVA", nullable = false)
    private int numeroTva;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        SocietyProducer that = (SocietyProducer) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, email, phone, numeroTva);
    }
}
