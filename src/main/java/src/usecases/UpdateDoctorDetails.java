package src.usecases;


import lombok.Getter;
import lombok.Setter;
import src.entities.Doctor;
import src.entities.Patient;
import src.persistence.DoctorsDAO;
import src.persistence.PatientsDAO;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
}
