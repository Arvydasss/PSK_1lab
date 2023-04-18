package src.usecases;

import lombok.Getter;
import lombok.Setter;
import src.mybatis.dao.DepartmentMapper;
import src.mybatis.model.Department;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class DepartmentMyBatis {
    @Inject
    private DepartmentMapper departmentMapper;

    @Getter
    private List<src.mybatis.model.Department> allDepartments;

    @Getter @Setter
    private Department departmentToCreate = new Department();

    @PostConstruct
    public void init() {
        this.loadAllDepartments();
    }

    private void loadAllDepartments() {
        this.allDepartments = departmentMapper.selectAll();
    }

    @Transactional
    public String createDepartment() {
        departmentMapper.insert(departmentToCreate);
        return "/myBatis/departments?faces-redirect=true";
    }
}
