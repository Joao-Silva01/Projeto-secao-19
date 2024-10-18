package Model.dao.impl;

import Model.entities.Department;
import Model.entities.Seller;
import Model.dao.SellerDAO;
import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDAO {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    +"FROM seller INNER JOIN department "
                    +"ON seller.DepartmentId = department.Id "
                    +"WHERE seller.Id = ? ");
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                Department dep = instantiateDepartment(rs);
                Seller sll = instantiateSeller(rs, dep);
                return sll;
            }
            return null;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }

    }

    public Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    public Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller sll = new Seller();
        sll.setId(rs.getInt("Id"));
        sll.setName(rs.getString("Name"));
        sll.setEmail(rs.getString("Email"));
        sll.setBaseSalary(rs.getDouble("BaseSalary"));
        sll.setBirthDate(rs.getDate("BirthDate"));
        sll.setDepartment(dep);
        return sll;
    }
    @Override
    public List<Seller> findAll() {
        return List.of();
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                            +"FROM seller INNER JOIN department "
                            +"ON seller.DepartmentId = department.Id "
                            +"WHERE DepartmentId = ? "
                            +"ORDER BY Name");

            ps.setInt(1,department.getId());
            rs = ps.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while(rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }
                Seller sll = instantiateSeller(rs, dep);
                list.add(sll);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }
}
