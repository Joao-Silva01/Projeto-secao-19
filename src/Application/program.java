package Application;

import Model.dao.DaoFactory;
import Model.dao.DepartmentDAO;
import Model.dao.SellerDAO;
import Model.entities.Department;
import Model.entities.Seller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class program {
    public void main(String[] a){

        SellerDAO sellerDAO = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 1: seller findByDepartment ===");
        Department dep = new Department(4,null);
        List<Seller> list = sellerDAO.findByDepartment(dep);

        for (Seller sl : list) {
            System.out.println(sl);
        }
    }
}
