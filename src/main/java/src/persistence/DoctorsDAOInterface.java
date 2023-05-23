package src.persistence;
import src.entities.Doctor;

public interface DoctorsDAOInterface {
    void persist(Doctor doctor);
    Doctor findOne(Integer id);
    Doctor update(Doctor doctor);
    void delete(Integer id);
    String findRandomDoctorName();
}

