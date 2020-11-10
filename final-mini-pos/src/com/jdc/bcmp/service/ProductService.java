package com.jdc.bcmp.service;

import com.jdc.bcmp.entity.Product;

public class ProductService {

	private static ProductService INSTANCE;
	
	private ProductService() {}
	
	public static ProductService getInstance() {
		return null == INSTANCE ? INSTANCE = new ProductService() : INSTANCE;
	}
	
	public void save(Product p) {
		
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
