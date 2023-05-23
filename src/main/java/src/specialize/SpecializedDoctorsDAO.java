package src.specialize;

import src.entities.Doctor;
import src.entities.Patient;
import src.persistence.DoctorsDAO;
import src.persistence.DoctorsDAOInterface;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Random;

@Specializes
@ApplicationScoped
public class SpecializedDoctorsDAO extends DoctorsDAO {

    @Inject
    private EntityManager em;

    public void persist(Doctor doctor) {
        validateDoctorName(doctor); // Validate the doctor's name

        this.em.persist(doctor);
    }

    private void validateDoctorName(Doctor doctor) {
        String name = doctor.getName();

        if (!Character.isUpperCase(name.charAt(0))) {
            throw new IllegalArgumentException("Doctor name must start with a capital letter.");
        }
    }
}

