package com.leysoft.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class Util {
	
	private Util() {}
	
	public static String queryReplace(String query, Object... values) {
		return String.format(query, values);
	}
	
	public static <T> List<T> queryResult(JestClient client, String query,
			String index, String type, Class<T> clazz) {
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
}
