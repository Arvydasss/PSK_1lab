package src.persistence;

import src.entities.Department;
import src.entities.Doctor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class DepartmentsDAO {

    @Inject
    private EntityManager em;

    public List<Department> loadAll() {
        return em.createNamedQuery("Department.findAll", Department.class).getResultList();
    }

    public void persist(Department department){
        this.em.persist(department);
    }

    public Department findOne(Integer id) {
        return em.find(Department.class, id);
    }

    public void delete(Integer id) {
        Department department = em.find(Department.class, id);
        if (department != null) {
            em.remove(department);
        }
    }
}
