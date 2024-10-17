package Secao19Projeto;

    import Entities.Product;

import java.util.List;

public interface ProductDAO {
    void insert(Product obj);
    void update(Product obj);
    void deleteById(Integer id);
    List<Product> findAll();
}
