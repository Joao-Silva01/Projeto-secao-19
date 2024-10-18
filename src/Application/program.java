package Application;

import Model.dao.DaoFactory;
import Model.dao.SellerDAO;
import Model.entities.Department;
import Model.entities.Seller;

import java.sql.Date;
import java.time.LocalDate;

public class program {
    public void main(String[] a){

        SellerDAO sellerDAO = DaoFactory.createSellerDao();

        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);
    }
}
