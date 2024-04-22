package com.cqx.qxmall.product.feign;

import com.cqx.common.to.es.SkuEsModel;
import com.cqx.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/22 12:06
 */
@FeignClient("qxmall-search")
public interface SearchFeignService {

    @PostMapping("/search/save/product")
    public R productStatusUp (@RequestBody List<SkuEsModel> skuEsModelList);
}
