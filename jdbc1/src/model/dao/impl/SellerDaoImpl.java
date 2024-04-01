package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import db.DB;
import db.execeptions.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoImpl implements SellerDao{

	private Connection connection;
	
	public SellerDaoImpl (Connection conn) {
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
			if(rs.next() == true) {
				int departmentId = rs.getInt("DepartmentId");
				String departmentName = rs.getString("DepName");
				Department department = new Department(departmentId, departmentName);
				String name = rs.getString("Name");
				String email = rs.getString("email");
				Date birthDate = rs.getDate("BirthDate");
				double baseSalary = rs.getDouble("BaseSalary");
				seller = new Seller(id, name, email, birthDate, baseSalary, department);
			}
			
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		return seller;
	}

	@Override
	public List<Seller> findAll() {
		return null;
	}

}
