package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.execeptions.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoImpl implements SellerDao {

	private Connection connection;

	public SellerDaoImpl(Connection conn) {
		this.connection = conn;
	}

	@Override
	public void insert(Seller seller) {

	}

	@Override
	public void update(Seller seller) {

	}

	@Override
	public void deletebyId(Integer id) {

	}

	@Override
	public Seller findById(Integer id) {
		Seller seller = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT s.*, p.name as DepName\r\n"
					+ "FROM seller s INNER JOIN department p\r\n" 
					+ "ON s.DepartmentId = p.id\r\n" 
					+ "WHERE s.id = ?;");
			ps.setInt(1, id);

			rs = ps.executeQuery();
			if (rs.next() == true) {
				Department department = instantiateDepartment(rs);
				seller = instantiateSeller(rs, department); 
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		return seller;
	}

	private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
		
		int id = rs.getInt("Id");
		String name = rs.getString("Name");
		String email = rs.getString("email");
		Date birthDate = rs.getDate("BirthDate");
		double baseSalary = rs.getDouble("BaseSalary");
		
		return new Seller(id, name, email, birthDate, baseSalary, department);

	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		
		int departmentId = rs.getInt("DepartmentId");
		String departmentName = rs.getString("DepName");
		
		return new Department(departmentId, departmentName);
	
	}

	@Override
	public List<Seller> findAll() {
		List<Seller> list = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT s.*, p.name as DepName\r\n"
					+ "FROM seller s INNER JOIN department p\r\n"
					+ "ON s.DepartmentId = p.id\r\n"
					+ "ORDER BY Name;");
			
			rs = ps.executeQuery();
			
			Map<Integer, Department> mapDepartment = new HashMap<>();
			
			while(rs.next() == true) {

				Department dep = mapDepartment.get(rs.getInt("DepartmentId"));  
				if(dep == null) {
					dep = instantiateDepartment(rs);
					mapDepartment.put(dep.getId(), dep);
				}

				Seller seller = instantiateSeller(rs, dep); 
				list.add(seller);
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		return list;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {

		List<Seller> list = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT s.*, p.name as DepName\r\n"
					+ "FROM seller s INNER JOIN department p\r\n"
					+ "ON s.DepartmentId = p.id\r\n"
					+ "WHERE s.DepartmentId = ?\r\n"
					+ "ORDER BY Name;");
			
			ps.setInt(1, department.getId());

			rs = ps.executeQuery();
			
			Map<Integer, Department> mapDepartment = new HashMap<>();
			
			while(rs.next() == true) {

				Department dep = mapDepartment.get(rs.getInt("DepartmentId"));  
				if(dep == null) {
					dep = instantiateDepartment(rs);
					mapDepartment.put(dep.getId(), dep);
				}

				Seller seller = instantiateSeller(rs, dep); 
				list.add(seller);
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		return list;
	}

}
