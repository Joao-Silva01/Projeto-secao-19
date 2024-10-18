package Model.dao;

import Model.dao.impl.SellerDaoJDBC;
import db.DB;

public class DaoFactory {
    public static SellerDAO createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }
}
