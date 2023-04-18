package src.mybatis.dao;

import org.mybatis.cdi.Mapper;
import src.mybatis.model.Department;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.DEPARTMENT
     *
     * @mbg.generated Tue Apr 18 20:09:24 EEST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.DEPARTMENT
     *
     * @mbg.generated Tue Apr 18 20:09:24 EEST 2023
     */
    int insert(Department record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.DEPARTMENT
     *
     * @mbg.generated Tue Apr 18 20:09:24 EEST 2023
     */
    Department selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.DEPARTMENT
     *
     * @mbg.generated Tue Apr 18 20:09:24 EEST 2023
     */
    List<Department> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.DEPARTMENT
     *
     * @mbg.generated Tue Apr 18 20:09:24 EEST 2023
     */
    int updateByPrimaryKey(Department record);
}