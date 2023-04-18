package src.persistence;

import src.entities.Doctor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class DoctorsDAO {

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
}
