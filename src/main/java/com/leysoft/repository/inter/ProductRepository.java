package com.leysoft.repository.inter;

import java.util.List;

import com.leysoft.document.Product;

public interface ProductRepository {
	
	public List<Product> findByStoreId(String storeId);
}
