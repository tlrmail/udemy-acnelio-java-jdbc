package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program005 {

	public static void main(String[] args) {

		Department department = new Department(4, "Books");
		Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.00, department);

		System.out.println(department);
		System.out.println(seller);

		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println();
		System.out.println("*** TESTE 01: seller findById ***");
		Seller seller02 = sellerDao.findById(3);

		System.out.println(seller02);

		System.out.println();
		System.out.println("*** TESTE 02: seller findByDepartment ***");
		Department department02 = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department02);

		for (Seller s : list) {
			System.out.println(s);
		}

		System.out.println();
		System.out.println("*** TESTE 03: seller findAll ***");
		List<Seller> list02 = sellerDao.findAll();

		for (Seller s : list02) {
			System.out.println(s);
		}

		System.out.println();
		System.out.println("*** TESTE 04: seller insert ***");
		Seller seller03 = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.00, department);
		sellerDao.insert(seller03);
		System.out.println("Inserted! New id = " + seller03.getId());

		System.out.println();
		System.out.println("*** TESTE 05: seller update ***");
		Seller seller04 = sellerDao.findById(10); // new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.00,
													// department);
		seller04.setName("Marta Waine");
		seller04.setEmail("Marta @gmail.com");
		sellerDao.update(seller04);
		System.out.println("Update completed");

		System.out.println();
		System.out.println("*** TESTE 06: seller delete ***");
		System.out.println("Enter id for delete test:");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		sc.close();
		sellerDao.deleteById(id);
		System.out.println("Deleted completed");
	}
}
