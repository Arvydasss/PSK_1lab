package src.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Department.findAll", query = "select a from Department as a")
})
@Table(name = "DEPARTMENT")
@Getter @Setter
public class Department {

    public Department(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = {CascadeType.ALL})
    private List<Doctor> doctors = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department department = (Department) o;
        return Objects.equals(name, department.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
