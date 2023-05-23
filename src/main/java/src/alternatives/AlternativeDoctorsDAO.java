package src.alternatives;

import src.entities.Department;
import src.entities.Doctor;
import src.persistence.DepartmentsDAO;
import src.persistence.DoctorsDAOInterface;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Alternative
public class AlternativeDoctorsDAO implements DoctorsDAOInterface {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Doctor doctor) {
        validateDepartmentName(doctor); // Validate the department's name
        this.em.persist(doctor);
         // Call the original implementation
    }
    @Override
    public Doctor findOne(Integer id){
        return em.find(Doctor.class, id);
    }
    @Override
    public Doctor update(Doctor doctor){
        return em.merge(doctor);
    }
    @Override
    public void delete(Integer id) {
        Doctor doctor = em.find(Doctor.class, id);
        if (doctor != null) {
            em.remove(doctor);
        }
    }

    @Override
    public String findRandomDoctorName() {
        return null;
    }


    private void validateDepartmentName(Doctor doctor) {
        String name = doctor.getName();
        String[] words = name.split(" ");
        // Validate the department name starts with a capital letter
        if (!Character.isUpperCase(name.charAt(0)) || words.length > 3) {
            throw new IllegalArgumentException("Doctor name must start with a capital letter and cannot be more than 3 words.");
        }
    }
}


