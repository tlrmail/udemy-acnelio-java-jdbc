package application;

import java.util.Date;
import java.util.List;

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
		
		for(Seller s: list) {
			System.out.println(s);
		}
}
}
