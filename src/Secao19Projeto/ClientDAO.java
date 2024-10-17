package Secao19Projeto;

import Entities.Client;

import java.util.List;

public interface ClientDAO {
    void insert(Client obj);
    void update(Client obj);
    void deleteById(Integer id);
    List<Client> findAll();
}
