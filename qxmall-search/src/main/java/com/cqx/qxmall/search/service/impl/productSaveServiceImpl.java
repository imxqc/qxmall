package com.cqx.qxmall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.cqx.common.to.es.SkuEsModel;
import com.cqx.qxmall.search.config.qxmallElasticSearchConfig;
import com.cqx.qxmall.search.constant.EsConstant;
import com.cqx.qxmall.search.service.productSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/22 11:50
 */
@Slf4j
@Service
public class productSaveServiceImpl implements productSaveService {
    @Autowired
    RestHighLevelClient restHighLevelClient;


    @Override
    public Boolean productStatusUp(List<SkuEsModel> skuEsModelList) throws IOException {
        //bulk 为批量保存
        BulkRequest bulkRequest = new BulkRequest();

        for (SkuEsModel skuEsModel : skuEsModelList) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(skuEsModel.getSkuId().toString());
            String string = JSON.toJSONString(skuEsModel);
            indexRequest.source(string, XContentType.JSON);

            //将indexrequest信息加入bulkrequest
            bulkRequest.add(indexRequest);
        }


        //批量保存索引
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, qxmallElasticSearchConfig.COMMON_OPTIONS);

        boolean b = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> item.getId()).collect(Collectors.toList());
        if (b){
            log.error("商品上架错误:{}",collect);
        }
        return b;
    }
}
