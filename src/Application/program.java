package Application;

import Model.dao.DaoFactory;
import Model.dao.DepartmentDAO;
import Model.dao.SellerDAO;
import Model.entities.Department;
import Model.entities.Seller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class program {
    public void main(String[] a){

        Scanner sc = new Scanner(System.in);

        SellerDAO sellerDAO = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);


        System.out.println("\n=== TEST 2: seller findByDepartment ===");
        Department dep = new Department(4,null);
        List<Seller> list = sellerDAO.findByDepartment(dep);

        for (Seller sl : list) {
            System.out.println(sl);
        }


        System.out.println("\n=== TEST 3: seller findAll ===");
        List<Seller> dp = sellerDAO.findAll();

        for (Seller sl : dp) {
            System.out.println(sl);
        }


        System.out.println("\n=== TEST 4: seller insert ===");
        Seller sll = new Seller(null,"Greg","greg@gmail.com", new Date(2004,10,12),4000.0,dep);
        //sellerDAO.insert(sll);
        System.out.println(sll.getId());

        System.out.println("\n=== TEST 5: seller update ===");
        Seller s = sellerDAO.findById(1);
        s.setName("Maria Ana");
        sellerDAO.update(s);
        System.out.println("Completed");

        System.out.println("\n=== TEST 6: seller delete ===");
        System.out.println("Delete test id: ");
        int test = sc.nextInt();
        sellerDAO.deleteById(test);

        sc.close();

    }
}
