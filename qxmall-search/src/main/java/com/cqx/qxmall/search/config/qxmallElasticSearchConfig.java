package com.cqx.qxmall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/21 18:58
 */
@Configuration
public class qxmallElasticSearchConfig {

    public static final RequestOptions  COMMON_OPTIONS;
    static{
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();

        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestClientBuilder restClientBuilder = null;
        restClientBuilder = RestClient.builder(new HttpHost("192.168.149.100",9200,"http"));

        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
        return client;
    }
}
