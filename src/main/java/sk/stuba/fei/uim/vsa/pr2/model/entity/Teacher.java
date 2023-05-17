package sk.stuba.fei.uim.vsa.pr2.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "TEACHER")
@NamedQuery(name = Teacher.FIND_ALL_QUERY, query = "select t from Teacher t")
@NamedQuery(name = Teacher.FIND_BY_EMAIL_QUERY, query = "select t from Teacher t where t.email = :email")
public class Teacher implements Serializable {
    private static final long serialVersionUID = -3294165768183131788L;

    public static final String FIND_ALL_QUERY = "Teacher.findAll";
    public static final String FIND_BY_EMAIL_QUERY = "Teacher.findByEmail";

    @Id
    private Long aisId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String institute;

//    @Column(nullable = false)
    private String department;

    @OneToMany(mappedBy = "supervisor", cascade = {CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Thesis> supervisedTheses;

    public Teacher() {
    }
}
