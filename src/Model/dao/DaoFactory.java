package Model.dao;

import Model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDAO createSellerDao(){
        return new SellerDaoJDBC();
    }
}
