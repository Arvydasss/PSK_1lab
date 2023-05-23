package src.usecases;


import lombok.Getter;
import lombok.Setter;
import src.entities.Doctor;
import src.entities.Patient;
import src.interceptors.LoggedInvocation;
import src.persistence.DoctorsDAO;
import src.persistence.PatientsDAO;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.io.Writer;
import java.util.Map;

@ViewScoped
@Named
@Getter @Setter
public class UpdateDoctorDetails implements Serializable {

    private Doctor doctor;

    private Patient patient = new Patient();

    private Integer docId;

    @Inject
    private DoctorsDAO doctorsDAO;

    @Inject
    private PatientsDAO patientsDAO;

    @PostConstruct
    private void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer doctorId = Integer.parseInt(requestParameters.get("doctorId"));
        this.doctor = doctorsDAO.findOne(doctorId);
    }

    @Transactional
    public String addDoctorPatient() {
        Patient dbPatient = patientsDAO.findOneByName(this.patient.getName());
        if(dbPatient != null){
            this.patient = dbPatient;
        }
        this.doctor.getPatients().add(this.patient);
        doctorsDAO.update(this.doctor);
        return "/doctorDetails.xhtml?faces-redirect=true&doctorId=" + this.doctor.getId();
    }

    @Transactional
    public String removeDoctor() {
        doctorsDAO.delete(doctor.getId());
        return "/doctors.xhtml?faces-redirect=true&departmentId=" + this.doctor.getDepartment().getId();
    }
    @Transactional
    public String addSamePatientToDifferentDoctor() {
        // Retrieve the patient object from the patient name input field
        Patient dbPatient = patientsDAO.findOneByName(this.patient.getName());
        Doctor dbDoctor = doctorsDAO.findOne(docId);
        dbDoctor.getPatients().add(dbPatient);
        doctorsDAO.update(dbDoctor);
        return "/doctorDetails.xhtml?faces-redirect=true&doctorId=" + this.doctor.getId();
    }

    @Transactional
    @LoggedInvocation
    public String updateDoctorName() {
        try{
            doctorsDAO.update(this.doctor);
        } catch (OptimisticLockException e) {
            return "/doctorDetails.xhtml?faces-redirect=true&doctorId=" + this.doctor.getId() + "&error=optimistic-lock-exception";
        }
        return "doctors.xhtml?&faces-redirect=true&departmentId=" + this.doctor.getDepartment().getId() + "&faces-redirect=true";
    }
}
