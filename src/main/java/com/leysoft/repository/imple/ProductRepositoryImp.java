
package com.leysoft.repository.imple;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import com.leysoft.document.Product;
import com.leysoft.dto.ScrollResponse;
import com.leysoft.repository.inter.ProductRepository;
import com.leysoft.util.ElasticsearchUtil;

import io.searchbox.client.JestClient;

@Repository
public class ProductRepositoryImp implements ProductRepository {

    private static final String QUERY_FIND_BY_STORE_ID =
            "{\"_source\": {\"includes\": [\"id\", \"name\", \"price\", \"store_id\"]},"
                    + " \"query\": {\"bool\": {\"filter\": {\"term\": {\"store_id\": %s}}}}}";

    @Value(
            value = "${elasticsearch.index}")
    private String index;

    @Value(
            value = "${elasticsearch.type}")
    private String type;

    @Value(
            value = "${elasticsearch.scroll.minutes-to-live}")
    private String scrollMinutesToLive;

    @Autowired
    private JestClient jestClient;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public List<Product> findAllByStoreId(String storeId) {
        String query = ElasticsearchUtil.queryReplace(QUERY_FIND_BY_STORE_ID, storeId);
        return ElasticsearchUtil.queryResult(jestClient, query, index, type, Product.class);
    }

    @Override
    public List<Product> findAllByStoreIdScroll(String storeId, Long size) {
        String query = ElasticsearchUtil.queryReplace(QUERY_FIND_BY_STORE_ID, storeId);
        List<Product> result = new ArrayList<>();
        ScrollResponse<Product> scroll = ElasticsearchUtil.getScroll(jestClient, query, index, type,
                scrollMinutesToLive, size, Product.class);
        result.addAll(scroll.getResults());
        List<Product> temporalResult = new ArrayList<>();
        do {
            temporalResult = Objects.nonNull(scroll.getScrollId())
                    ? ElasticsearchUtil.queryResultByScrollId(jestClient, scroll.getScrollId(),
                            scrollMinutesToLive, size, Product.class)
                    : Collections.emptyList();
            if (!temporalResult.isEmpty()) {
                result.addAll(temporalResult);
            }
        }
        while (!temporalResult.isEmpty());
        return result;
    }

    @Override
    public List<Product> findByStoreId(Long storeId) {
        return null;
    }

    @Override
    public List<Product> findByName(String name, String... fields) {
        QueryBuilder matchQuery = matchQuery("name", name).operator(Operator.AND);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQuery).fetchSource(fields, null);
        String query = searchSourceBuilder.toString();
        return ElasticsearchUtil.queryResult(jestClient, query, index, type);
    }

    @Override
    public List<Product> findByNameAndLtePrice(String name, Double price, int from, int size) {
        List<Product> result = null;
        try {
            String query = ElasticsearchUtil.loadFile(resourceLoader,
                    "classpath:query/find_by_name_and_price.js");
            query = ElasticsearchUtil.queryReplace(query, from, size, name, price);
            result = ElasticsearchUtil.queryResult(jestClient, query, index, type);
        } catch (IOException e) {
            result = new ArrayList<>();
        }
        return result;
    }
}
