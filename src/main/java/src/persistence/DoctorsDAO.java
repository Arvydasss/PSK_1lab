package src.persistence;

import src.entities.Doctor;
import src.entities.Patient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class DoctorsDAO implements DoctorsDAOInterface{

    @Inject
    private EntityManager em;

    public void persist(Doctor doctor){
        this.em.persist(doctor);
    }

    public Doctor findOne(Integer id){
        return em.find(Doctor.class, id);
    }

    public Doctor update(Doctor doctor){
        return em.merge(doctor);
    }

    public void delete(Integer id) {
        Doctor doctor = em.find(Doctor.class, id);
        if (doctor != null) {
            em.remove(doctor);
        }
    }

    public String findRandomDoctorName() {
        List<Doctor> doctors = em.createNamedQuery("Doctor.findAll", Doctor.class).getResultList();
        if (doctors.isEmpty()) {
            return null;
        } else {
            Random random = new Random();
            int index = random.nextInt(doctors.size());
            return doctors.get(index).getName();
        }
    }
}
