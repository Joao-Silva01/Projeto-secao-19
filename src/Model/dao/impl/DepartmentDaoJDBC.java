package Model.dao.impl;

import Model.entities.Department;
import Model.dao.DepartmentDAO;
import Model.entities.Seller;
import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDAO {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)");

            ps.setString(1,obj.getName());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE department " +
                    "SET Name = ? " +
                    "WHERE Id = ?");


            ps.setString(1,obj.getName());
            ps.setInt(2,obj.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("DELETE FROM department WHERE Id = ?");

            ps.setInt(1,id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement("SELECT department.Id as Id, department.name as Name FROM department WHERE Id = ?");

            ps.setInt(1,id);

            rs = ps.executeQuery();
            if(rs.next()){
                Department dep = instantiateDepartment(rs);
                return dep;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }

    }


    @Override
    public List<Department> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement("SELECT * FROM department");

            rs = ps.executeQuery();
            List<Department> list = new ArrayList<>();
            while(rs.next()){
                Department dep = new Department();
                dep.setId(rs.getInt("Id"));
                dep.setName(rs.getString("Name"));
                list.add(dep);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("id"));
        dep.setName(rs.getString("name"));
        return dep;
    }
}


