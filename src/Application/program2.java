package Application;

import Model.dao.DaoFactory;
import Model.dao.DepartmentDAO;
import Model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class program2 {
    public void main(String[] a) {

        Scanner sc = new Scanner(System.in);

        DepartmentDAO departmentDAO = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: department findById ===");
        Department sd = departmentDAO.findById(1);
        System.out.println(sd);

        System.out.println("\n=== TEST 3: department findAll ===");
        List<Department> depAll = departmentDAO.findAll();
        for (Department d : depAll) {
            System.out.println(d);
        }

        System.out.println("\n=== TEST 4: department insert ===");
        Department dep = new Department(null, "Pedreiro");
        //departmentDAO.insert(dep);
        System.out.println("Insert: " + dep.getId() + " - " + dep.getName());

        System.out.println("\n=== TEST 5: department update ===");
        Department depUp = new Department(9, "McBurger");
        System.out.println("Update: " + dep.getId() + " - " + dep.getName());
        //departmentDAO.update(depUp);


        System.out.println("\n=== TEST 6: department delete ===");
        System.out.println("Delete test id: ");
        int test = sc.nextInt();
        departmentDAO.deleteById(test);
        System.out.println("DELETE Id: "+ test);

        sc.close();

    }
}
