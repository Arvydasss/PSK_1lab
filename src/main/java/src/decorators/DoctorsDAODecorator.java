package src.decorators;

import src.entities.Doctor;
import src.persistence.DoctorsDAO;
import src.persistence.DoctorsDAOInterface;

import javax.annotation.Priority;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.interceptor.Interceptor;

@Decorator
@Priority(Interceptor.Priority.APPLICATION)
public abstract class DoctorsDAODecorator implements DoctorsDAOInterface {

    @Inject
    @Delegate
    @Any
    private DoctorsDAOInterface doctorsDAO; // Inject the original DoctorsDAO implementation

    @Override
    public void persist(Doctor doctor) {
        if ("Priėmimo".equals(doctor.getDepartment().getName())) {
            throw new IllegalArgumentException("Cannot persist Doctor with department name 'Priėmimo'");
        }

        doctorsDAO.persist(doctor);
    }

    @Override
    public Doctor findOne(Integer id) {
        return doctorsDAO.findOne(id);
    }

    @Override
    public Doctor update(Doctor doctor) {
        return doctorsDAO.update(doctor);
    }

    @Override
    public void delete(Integer id) {
        doctorsDAO.delete(id);
    }

    @Override
    public String findRandomDoctorName() {
        return doctorsDAO.findRandomDoctorName();
    }
}

