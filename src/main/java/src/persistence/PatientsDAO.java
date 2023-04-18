package src.persistence;

import src.entities.Patient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class PatientsDAO {

    @Inject
    private EntityManager em;

    public void persist(Patient patient){
        this.em.persist(patient);
    }

    public List<Patient> loadAll() {
        return em.createNamedQuery("Patient.findAll", Patient.class).getResultList();
    }
    public Patient findOneByName(String name){
        return loadAll().stream().filter(x -> name.equals(x.getName())).findFirst().orElse(null);
    }

    public Patient update(Patient patient){
        return em.merge(patient);
    }
}
