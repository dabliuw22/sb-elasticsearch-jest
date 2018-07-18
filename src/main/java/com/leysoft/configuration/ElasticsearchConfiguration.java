
package com.leysoft.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

@Configuration
public class ElasticsearchConfiguration {

    @Value(
            value = "${elasticsearch.cluster-nodes:http://localhost:9300}")
    private String clusterNode;

    @Value(
            value = "${elasticsearch.multi-threaded:true}")
    private boolean multiThreaded;

    @Value(
            value = "${elasticsearch.default-max-connection:2}")
    private int defaultMaxConnection;

    @Value(
            value = "${elasticsearch.max-connection:10}")
    private int maxConnection;

    @Value(
            value = "${elasticsearch.time-out.read}")
    private int readTimeout;

    @Value(
            value = "${elasticsearch.time-out.connection}")
    private int connectionTimeout;

    @Bean
    public HttpClientConfig httpClientConfig() {
        return new HttpClientConfig.Builder(clusterNode).multiThreaded(multiThreaded)
                .defaultMaxTotalConnectionPerRoute(defaultMaxConnection)
                .maxTotalConnection(maxConnection).connTimeout(connectionTimeout)
                .readTimeout(readTimeout).build();
    }

    @Bean
    public JestClientFactory jestClientFactory(HttpClientConfig config) {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(config);
        return factory;
    }

    @Bean
    public JestClient jestClient(JestClientFactory factory) {
        return factory.getObject();
    }
}
