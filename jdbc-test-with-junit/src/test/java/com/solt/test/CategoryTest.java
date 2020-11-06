package com.solt.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.solt.dao.ProductDao;
import com.solt.dao.SQLConnection;
import com.solt.dto.Category;
import com.solt.dto.Product;

class CategoryTest {

	private static ProductDao proDao;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		proDao = new ProductDao();
		SQLConnection.dropTableOnStart("category");
	}

	@Test
	void insert_product_when_category_is_empty() {
		Product p = new Product();
		Category c = new Category();
		
		p.setId(1);
		p.setName("Cloret");
		p.setPrice(200);
		p.setDescription("Chweing Gum");
		
		c.setId(1);
		c.setName("Snacks");
		
		proDao.save(p);
		
		
	}

}
