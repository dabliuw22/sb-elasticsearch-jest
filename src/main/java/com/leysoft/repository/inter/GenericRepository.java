
package com.leysoft.repository.inter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leysoft.dto.ScrollResponse;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchScroll;
import io.searchbox.params.Parameters;

public interface GenericRepository<T> {

    Logger LOGGER = LoggerFactory.getLogger(GenericRepository.class);

    String SCROLL_ID_KEY = "_scroll_id";

    default ScrollResponse<T> findScroll(JestClient client, String query, String index, String type,
            String scroll, Long size, Class<T> clazz) {
        ScrollResponse<T> resultScroll = null;
        try {
            LOGGER.info("Scroll Query -> {}", query);
            Search search = new Search.Builder(query).addIndex(index).addType(type)
                    .setParameter(Parameters.SCROLL, scroll).setParameter(Parameters.SIZE, size)
                    .build();
            JestResult result = client.execute(search);
            resultScroll = new ScrollResponse<T>(
                    result.getJsonObject().get(SCROLL_ID_KEY).getAsString(), toList(result, clazz));
            LOGGER.info("Result scroll: {}", resultScroll);
        } catch (Exception e) {
            LOGGER.error("Error getting the scroll for the query: [{}]... ", query, e);
        }
        return resultScroll;
    }

    default ScrollResponse<T> findByScrollId(JestClient client, String scrollId, String scroll,
            Class<T> clazz) {
        ScrollResponse<T> resultQuery = null;
        try {
            LOGGER.info("Try get result by scrollId: [{}]...", scrollId);
            SearchScroll searchScroll = new SearchScroll.Builder(scrollId, scroll).build();
            JestResult result = client.execute(searchScroll);
            resultQuery = new ScrollResponse<T>(
                    result.getJsonObject().get(SCROLL_ID_KEY).getAsString(), toList(result, clazz));
            LOGGER.info("Result set: {}", resultQuery);
        } catch (Exception e) {
            LOGGER.error("Error obtain results by scrollId: [{}]... ", scrollId, e);
        }
        return resultQuery;
    }

    default List<T> findAll(JestClient client, String query, String index, String type,
            Class<T> clazz) {
        LOGGER.info("Query -> {}", query);
        List<T> resultQuery = null;
        try {
            Search search = new Search.Builder(query).addIndex(index).addType(type).build();
            JestResult result = client.execute(search);
            resultQuery = toList(result, clazz);
        } catch (Exception e) {
            LOGGER.error("Error executing the query: [{}]... ", query, e);
        }
        return Objects.nonNull(resultQuery) ? resultQuery : Collections.emptyList();
    }

    default List<T> toList(JestResult result, Class<T> clazz) {
        return result.getSourceAsObjectList(clazz);
    }
}
