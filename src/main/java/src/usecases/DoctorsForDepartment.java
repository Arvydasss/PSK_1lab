package src.usecases;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import src.entities.Department;
import src.entities.Doctor;
import src.persistence.DepartmentsDAO;
import src.persistence.DoctorsDAO;

@Model
public class DoctorsForDepartment implements Serializable {

    @Inject
    private DepartmentsDAO departmentsDAO;

    @Inject
    private DoctorsDAO doctorsDAO;

    @Getter @Setter
    private Department department;

    @Getter @Setter
    private Doctor doctorToCreate = new Doctor();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer departmentId = Integer.parseInt(requestParameters.get("departmentId"));
        this.department = departmentsDAO.findOne(departmentId);
    }

    @Transactional
    public void createDoctor() {
        doctorToCreate.setDepartment(this.department);
        doctorsDAO.persist(doctorToCreate);
    }


    @Transactional
    public String removeDepartment() {
        departmentsDAO.delete(department.getId());
        return "/index.xhtml?faces-redirect=true";
    }
}
