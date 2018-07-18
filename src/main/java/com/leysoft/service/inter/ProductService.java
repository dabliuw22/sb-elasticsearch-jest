package com.leysoft.service.inter;

import java.util.List;

import com.leysoft.document.Product;

public interface ProductService {
	
	public List<Product> findByStoreId(String storeId);
}
