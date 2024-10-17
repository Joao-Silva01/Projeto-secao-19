package Application;

import Model.entities.Department;
import Model.entities.Seller;

import java.time.LocalDate;

public class program {
    public void main(String[] a){

        Department dp = new Department(1,"Computer");
        System.out.println(dp);
        System.out.println();

        Seller sll = new Seller(1,"Jonas","jonas@gmail", LocalDate.parse("2004-10-12"),2000.00,dp);
        System.out.println(sll);
    }
}
