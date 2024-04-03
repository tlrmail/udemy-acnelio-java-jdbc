package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.execeptions.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoImpl implements DepartmentDao {

	private Connection connection;

	public DepartmentDaoImpl(Connection conn) {
		this.connection = conn;
	}

	@Override
	public void insert(Department department) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO department(Name) "
					+ "VALUES"
					+ " (?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, department.getName());
			
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next() == true) {
					int id = rs.getInt(1);
					department.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void update(Department department) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("UPDATE department "
					+ "SET Name = ?"
					+ " WHERE Id = ? "
					, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, department.getName());
			ps.setInt(2, department.getId());
			ps.executeUpdate();

		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public void deleteById(Integer id) {

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("DELETE FROM department WHERE id = ?");

			ps.setInt(1, id);
			
			int rows = ps.executeUpdate();

			if(rows == 0) {
				throw new DbException("Não há registro com esse id.");
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public Department findById(Integer id) {
		Department department = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM department WHERE id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next() == true) {
				department = new Department(rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		return department;
	}

	@Override
	public List<Department> findAll() {
		List<Department> list = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM department;");
			
			rs = ps.executeQuery();
			
			while(rs.next() == true) {
				Department department = new Department(rs.getInt(1), rs.getString(2));
				list.add(department);
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
