package com.cqx.qxmall.search.controller;
import com.cqx.common.exception.BizCodeEnume;
import com.cqx.common.to.es.SkuEsModel;
import com.cqx.common.utils.R;
import com.cqx.qxmall.search.service.productSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/22 11:46
 */

@Slf4j
@RestController
@RequestMapping("/search/save")
public class ElasticSaveController {
    @Autowired
    productSaveService productSaveService;

    /**
     * 上架商品
     * @param skuEsModelList
     * @return
     */
    @PostMapping("/product")
    public R productStatusUp (@RequestBody List<SkuEsModel> skuEsModelList){
        Boolean res = null;

        try {
        res =  productSaveService.productStatusUp(skuEsModelList);
        } catch (Exception e) {
            log.error("商品上架错误:{}",e);
            return R.error(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(),BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg());
        }

        if (!res){
            return R.ok();
        }else {
            return R.error(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(),BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg());
        }

    }
}
