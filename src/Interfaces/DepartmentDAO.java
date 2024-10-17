package Interfaces;

    import Model.entities.Department;

import java.util.List;

public interface DepartmentDAO {
    void insert(Department obj);
    void update(Department obj);
    void deleteById(Integer id);
    List<Department> findAll();
}
