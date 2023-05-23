package src.rest;

import lombok.Getter;
import lombok.Setter;
import src.entities.Department;
import src.entities.Doctor;
import src.persistence.DepartmentsDAO;
import src.persistence.DoctorsDAO;
import src.rest.contracts.DoctorDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/doctors")
public class DoctorsController {

    @Inject
    @Setter @Getter
    private DoctorsDAO doctorsDAO;

    @Inject
    @Setter @Getter
    private DepartmentsDAO departmentsDAO;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        Doctor doctor = doctorsDAO.findOne(id);
        if (doctor == null) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setName(doctor.getName());
        doctorDto.setSurname(doctor.getSurname());
        doctorDto.setDepartmentName(doctor.getDepartment().getName());

        return Response.ok(doctorDto).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(
            @PathParam("id") final Integer doctorId,
            DoctorDto doctorData) {
        try {
            Doctor existingDoctor = doctorsDAO.findOne(doctorId);
            if (existingDoctor == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingDoctor.setName(doctorData.getName());
            existingDoctor.setSurname(doctorData.getSurname());
            doctorsDAO.update(existingDoctor);
            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(DoctorDto doctorData) {
        try {
            Doctor newDoctor = new Doctor();
            newDoctor.setName(doctorData.getName());
            newDoctor.setSurname(doctorData.getSurname());

            String departmentName = doctorData.getDepartmentName();

            // Get the Department entity based on the departmentName
            Department department = departmentsDAO.findByName(departmentName);
            if (department == null) {
                // Handle the case when the department does not exist
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Department with name '" + departmentName + "' does not exist.")
                        .build();
            }

            newDoctor.setDepartment(department);

            doctorsDAO.persist(newDoctor);

            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            // Handle any exceptions or validation errors here
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


}
