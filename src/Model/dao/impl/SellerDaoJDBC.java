package Model.dao.impl;

import Model.entities.Department;
import Model.entities.Seller;
import Model.dao.SellerDAO;
import db.DB;
import db.DbException;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDAO {

    private Connection conn;
    private SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");


    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(
                    "INSERT INTO seller "
                            +"(Name,Email,BirthDate,BaseSalary, DepartmentId) "
                            +"VALUES "
                            +"(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,obj.getName());
            ps.setString(2,obj.getEmail());
            ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            ps.setDouble(4,obj.getBaseSalary());
            ps.setInt(5,obj.getDepartment().getId());

            int rowsAffect = ps.executeUpdate();

            if(rowsAffect > 0){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }else {
                throw new DbException("Unexpected error! No rows affected!");
            }

        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(ps);
        }
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
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                            +"FROM seller INNER JOIN department "
                            +"ON seller.DepartmentId = department.Id "
                            +"ORDER BY Name");

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
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }

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
