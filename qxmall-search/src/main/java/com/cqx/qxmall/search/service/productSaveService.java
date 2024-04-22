package com.cqx.qxmall.search.service;

import com.cqx.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/22 11:48
 */
public interface productSaveService {


    Boolean productStatusUp(List<SkuEsModel> skuEsModelList) throws IOException;
}
