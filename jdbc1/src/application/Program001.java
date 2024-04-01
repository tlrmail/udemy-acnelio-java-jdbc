package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.mysql.jdbc.Statement;

import db.DB;


public class Program001 {
	
	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection connection = null;
		PreparedStatement ps = null;

		inserirSeller(sdf, connection, ps);
		inserirSellerComRetornoDoId(sdf, connection, ps);
		inserirDepartmentComRetornoDoId(sdf, connection, ps);
		closeSQL(ps);
	}
	
	public static void inserirSeller(SimpleDateFormat sdf, Connection connection, PreparedStatement ps) {
		try {
			connection = DB.getConnection();
			ps = connection.prepareStatement("INSERT INTO seller(Name, Email, BirthDate, BaseSalary, DepartmentId)" 
					+ " VALUES(?, ?, ?, ?, ?);");
			
			ps.setString(1, "Thiago Leite Ribeiro Campos");
			ps.setString(2, "tlrc@gmail.com");
			ps.setDate(3, new java.sql.Date(sdf.parse("21/11/1979").getTime()));
			ps.setDouble(4, 8000.0);
			ps.setInt(5, 1);
			
			int rowsAffected = ps.executeUpdate();
			
			System.out.println("Done! Rows affected: " + rowsAffected);
			
		}catch(SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}catch(ParseException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void inserirSellerComRetornoDoId(SimpleDateFormat sdf, Connection connection, PreparedStatement ps) {
		try {
			connection = DB.getConnection();
			ps = connection.prepareStatement("INSERT INTO seller(Name, Email, BirthDate, BaseSalary, DepartmentId)" 
					+ " VALUES(?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, "Thiago Leite Ribeiro Campos");
			ps.setString(2, "tlrc@gmail.com");
			ps.setDate(3, new java.sql.Date(sdf.parse("21/11/1979").getTime()));
			ps.setDouble(4, 8000.0);
			ps.setInt(5, 1);
			
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				while(rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done!(Seller) Id = " + id);
				}
			}else {
				System.out.println("No rown affected!");
			}
			
			System.out.println();
			
		}catch(SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}catch(ParseException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void inserirDepartmentComRetornoDoId(SimpleDateFormat sdf, Connection connection, PreparedStatement ps) {
		try {
			connection = DB.getConnection();
			ps = connection.prepareStatement("INSERT INTO department(Name)" 
					+ " VALUES ('D1'), ('D2')", Statement.RETURN_GENERATED_KEYS);
			
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				while(rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done!(Department) Id = " + id);
				}
			}else {
				System.out.println("No rown affected!");
			}
			
			System.out.println();
			
		}catch(SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void closeSQL(PreparedStatement ps) {
		DB.closeStatement(ps);
		DB.closeConnection();
	}
	
}
