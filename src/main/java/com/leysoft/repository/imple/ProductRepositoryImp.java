package com.leysoft.repository.imple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.leysoft.document.Product;
import com.leysoft.repository.inter.ProductRepository;
import com.leysoft.util.Util;

import io.searchbox.client.JestClient;

@Repository
public class ProductRepositoryImp implements ProductRepository {
	
	private static final String QUERY_FIND_BY_STORE_ID = "{\"_source\": {\"includes\": [\"id\", \"name\", \"price\", \"store_id\"]},"
			+ " \"query\": {\"bool\": {\"filter\": {\"term\": {\"store_id\": %s}}}}}";
	
	@Value(value = "${elasticsearch.index}")
	private String index;
	
	@Value(value = "${elasticsearch.type}")
	private String type;
	
	@Autowired
	private JestClient jestClient;
	
	@Override
	public List<Product> findByStoreId(String storeId) {
		String query = Util.queryReplace(QUERY_FIND_BY_STORE_ID, storeId);
		return Util.queryResult(jestClient, query, index, type, Product.class);
	}
}