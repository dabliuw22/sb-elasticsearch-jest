package com.leysoft.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

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
	
	@SuppressWarnings(value = {"deprecation"})
	public static <T> List<T> resultToList(SearchResult result, Class<T> clazz) {
		return result.getSourceAsObjectList(clazz);
	}
	
	public String loadFile(ResourceLoader resourceLoader, String fileName) throws IOException {
		Resource resource = resourceLoader.getResource(fileName);
		return Resources.toString(resource.getURL(), Charsets.UTF_8);
	}
}
