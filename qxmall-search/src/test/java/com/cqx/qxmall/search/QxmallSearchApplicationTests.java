package com.cqx.qxmall.search;

import com.alibaba.fastjson.JSON;
import com.cqx.qxmall.search.config.qxmallElasticSearchConfig;
import com.mysql.cj.xdevapi.JsonString;
import lombok.Data;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QxmallSearchApplicationTests {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Test
    public void contextLoads() {
        System.out.println(restHighLevelClient);
    }

    @Data
    public class User {
        public String name;
        public Integer age;
    }

    @Test
    public void indexData() throws IOException {
        //获取indexrequest对象, 传入存入es的索引名
        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.id("1");//设置数据id,不设置系统默认随机分配

        User user = new User();
        user.setAge(66);
        user.setName("ll");

        //获取user对应的json
        String jsonString = JSON.toJSONString(user);

        //传入json 并指定传入类型为json
        indexRequest.source(jsonString, XContentType.JSON);

        //执行操作
        IndexResponse index = restHighLevelClient.index(indexRequest, qxmallElasticSearchConfig.COMMON_OPTIONS);

        //获取响应
        System.out.println("index = " + index);
    }

    @Test
    public void testSearchData() throws IOException {
        // 1. 创建查询请求SearchRequest
        SearchRequest searchRequest = new SearchRequest();
        // 指定索引
        searchRequest.indices("bank");
        // 指定DSL 查询的条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 查询操作 query方法内传入条件
        // 通过QueryBuilders获取不同的查询条件, 比如下面的条件是 查询address含有mill的结果
        searchSourceBuilder.query(QueryBuilders.matchQuery("address", "mill"));

        // 设置分页操作
//        searchSourceBuilder.from();
//        searchSourceBuilder.size();

        //聚合操作 aggregation方法内传入条件
        // 通过AggregationBuilders获取不同的查询条件
        //按照年龄的值进行聚合 设聚合函数的名为ageAgg 且最多查询10条
        TermsAggregationBuilder age = AggregationBuilders.terms("ageAgg").field("age").size(10);
        searchSourceBuilder.aggregation(age);

        //计算平均薪资 设聚合函数的名为balanceAvg
        AvgAggregationBuilder balance = AggregationBuilders.avg("balanceAvg").field("balance");
        searchSourceBuilder.aggregation(balance);

        // 打印搜索条件
        System.out.println("searchSourceBuilder = " + searchSourceBuilder);
        // 将查询条件设置进 SearchRequest
        searchRequest.source(searchSourceBuilder);

        // 2.执行检索
        SearchResponse response = restHighLevelClient.search(searchRequest, qxmallElasticSearchConfig.COMMON_OPTIONS);

        // 3.分析结果
        System.out.println("response = " + response.toString());
        // 3.1获取所有查到的数据
        SearchHits hits = response.getHits(); //外层hit
        SearchHit[] searchHits = hits.getHits();// 内层hit,获取所有符合条件的数据
        for (SearchHit searchHit : searchHits) {
            //可以string(也可创建对应实体类转成实体类对象)获取,也可Map(根据字段名获取值)获取

            String sourceAsString = searchHit.getSourceAsString();//以string字符串获取hit中source的数据
        }


        // 3.2获取聚合分析信息
        Aggregations aggregations = response.getAggregations();

        //通过聚合函数名获取分析信息
        Terms ageAgg = aggregations.get("ageAgg");
        //获取对应的bucket
        List<? extends Terms.Bucket> buckets = ageAgg.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            String keyAsString = bucket.getKeyAsString();
            System.out.println("年龄 = " + keyAsString);
            long docCount = bucket.getDocCount();
            System.out.println("出现的次数 = " + docCount);
        }

    }

}
