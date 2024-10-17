package Model.dao;

import Model.entities.Seller;

import java.util.List;

public interface SellerDAO {
    void insert(Seller obj);
    void update(Seller obj);
    void deleteById(Integer id);
    List<Seller> findAll();
}
