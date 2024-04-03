package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program006 {

	public static void main(String[] args) {

		Department department = new Department(4, "Books");

		System.out.println(department);

		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

		System.out.println();
		System.out.println("*** TESTE 01: department findById ***");
		Department department01 = departmentDao.findById(3);

		System.out.println(department01);

		System.out.println();
		System.out.println("*** TESTE 02: department findAll ***");
		List<Department> list01 = departmentDao.findAll();

		for (Department d : list01) {
			System.out.println(d);
		}

		System.out.println();
		System.out.println("*** TESTE 04: department insert ***");
		Department department02 = new Department(null, "New Books");
		departmentDao.insert(department02);
		System.out.println("Inserted! New id = " + department02.getId());

		System.out.println();
		System.out.println("*** TESTE 05: department update ***");
		Department department03 = departmentDao.findById(10); 
		
		department03.setName("Food and Drugs");
		departmentDao.update(department03);
		System.out.println("Update completed");

		System.out.println();
		System.out.println("*** TESTE 06: department delete ***");
		System.out.println("Enter id for delete test:");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		sc.close();
		departmentDao.deleteById(id);
		System.out.println("Deleted completed");
	}
}
