package src.rest.contracts;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DoctorDto {

    private String name;

    private String surname;

    private String departmentName;

    private Integer version;
}
