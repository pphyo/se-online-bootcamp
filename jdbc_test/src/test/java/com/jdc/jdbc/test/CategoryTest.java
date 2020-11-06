package com.jdc.jdbc.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import com.jdc.db.CategoryManagement;
import com.jdc.db.entity.Category;

@TestMethodOrder(OrderAnnotation.class)
class CategoryTest {
	
	private static CategoryManagement catManagement;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		catManagement = new CategoryManagement();
		
//		Category c1 = new Category();
//		Category c2 = new Category();
//		Category c3 = new Category();
//		
//		c1.setName("Snacks");
//		c2.setName("Juice");
//		c3.setName("Meat");
//		
//		catManagement.save(c1, c2, c3);
		
	}

//	@Test
	@Order(2)
	void category_insert_test() {
		Category c = new Category();
		c.setName("Drinks");
	}

//	@Test
	@Order(1)
	void find_by_id_for_category() {
		Category c = catManagement.findById(2);
		assertNotEquals("Drinks", c.getName());
		
		Category c2 = catManagement.findById(1);
		assertEquals("Foods", c2.getName());
	}
	
	@Test
	@Order(3)
	void find_all_for_category() {
		List<Category> list = catManagement.findAll();
		assertNotNull(list);
		assertNotEquals(0, list.size());
		assertEquals(4, list.size());
	}
	
	@Test
	@Order(4)
	void delete_test_for_category() {
		Category c = catManagement.findById(5);
		System.out.println(c.getId());
		System.out.println(c.getName());
	}

}