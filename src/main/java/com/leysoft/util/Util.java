package com.leysoft.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.leysoft.document.Product;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class Util {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
	
	private Util() {}
	
	public static String queryReplace(String query, Object... values) {
		return String.format(query, values);
	}
	
	public static <T> List<T> queryResult(JestClient client, String query,
			String index, String type, Class<T> clazz) {
		LOGGER.info("query -> {}", query);
		List<T> resultQuery = null;
		try {
			Search search = new Search.Builder(query).addIndex(index).addType(type).build();
			SearchResult result = client.execute(search);
			resultQuery = resultToList(result, clazz);
		} catch (IOException e) {
			resultQuery = new ArrayList<>();
		}
		return resultQuery;
	}
	
	public static List<Product> queryResult(JestClient client, String query,
			String index, String type) {
		LOGGER.info("query -> {}", query);
		List<Product> resultQuery = null;
		try {
			Search search = new Search.Builder(query).addIndex(index).addType(type).build();
			SearchResult result = client.execute(search);
			resultQuery = resultToList(result);
		} catch (IOException e) {
			resultQuery = new ArrayList<>();
		}
		return resultQuery;
	}
	
	@SuppressWarnings(value = {"deprecation"})
	public static <T> List<T> resultToList(SearchResult result, Class<T> clazz) {
		return result.getSourceAsObjectList(clazz);
	}
	
	@SuppressWarnings(value = {"rawtypes", "unchecked"})
	public static List<Product> resultToList(SearchResult result) {
		List<SearchResult.Hit<HashMap, Void>> hits = result.getHits(HashMap.class);
		List<Product> products = new ArrayList<>();
		hits.forEach(hit -> products.add(mapToProduct(hit.source)));
		return products;
	}
	
	public static Product mapToProduct(Map<String, Object> source) {
		Product product = new Product();
		product.setId((String) source.get("id"));
		product.setName((String) source.get("name"));
		product.setPrice((Double) source.get("price"));
		product.setStoreId((Double) source.get("store_id"));
		return product;
	}
	
	public String loadFile(ResourceLoader resourceLoader, String fileName) throws IOException {
		Resource resource = resourceLoader.getResource(fileName);
		return Resources.toString(resource.getURL(), Charsets.UTF_8);
	}
}
