package com.jdc.jdbc.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.jdc.db.CategoryManagement;
import com.jdc.db.ProductManagement;
import com.jdc.db.entity.Category;
import com.jdc.db.entity.Product;

class ProductTest {

	private static ProductManagement pro;
	private static CategoryManagement cat;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		pro = new ProductManagement();
		cat = new CategoryManagement();
		
//		Product p1 = new Product("Burger", 3000, 1);
//		Product p2 = new Product("Myanmar", 2000, 2);
//		Product p3 = new Product("Pwint Phyu", 500, 3);
//		Product p4 = new Product("Ve Ve", 400, 4);
//		
//		pro.save(p1, p2, p3, p4);
		
//		Category c = cat.findById(1);
//		
//		pro.save(new Product("Pizza", 25000, c));
		
	}

	@Test
	void test() {
		List<Product> list = pro.getAll();
		assertNotNull(list);
//		assertEquals(6, list.size());
		
		Product p = pro.findById(1);
		assertEquals(1, p.getCategory().getId());
		assertEquals("Foods", p.getCategory().getName());
		assertEquals("Burger", p.getName());
		assertEquals(3000, p.getPrice());
		
		Product p2 = pro.findById(5);
		assertEquals(1, p2.getCategory().getId());
	}

}
