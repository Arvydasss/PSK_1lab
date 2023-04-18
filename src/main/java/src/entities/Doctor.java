package src.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@NamedQueries({
        @NamedQuery(name = "Doctor.findAll", query = "select a from Doctor as a")
})
@Table(name = "DOCTOR")
@Getter @Setter
public class Doctor implements Serializable {

    public Doctor() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    @Column(name = "NAME")
    private String name;

    @Column(name = "BOX_OFFICE")
    private Double boxOffice = 50.5;

    @ManyToOne
    @JoinColumn(name="DEPARTMENT_ID")
    private Department department;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "DOCTOR_PATIENTS",
            joinColumns = @JoinColumn(name = "DOCTOR_ID"),
            inverseJoinColumns = @JoinColumn(name = "PATIENT_ID"))
    private List<Patient> patients = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id) &&
                Objects.equals(name, doctor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Basic
    private String surname;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
