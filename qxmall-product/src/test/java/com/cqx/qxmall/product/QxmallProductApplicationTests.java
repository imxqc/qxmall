package com.cqx.qxmall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqx.qxmall.product.entity.BrandEntity;
import com.cqx.qxmall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class QxmallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Test
    public void test(){
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setName("xiaomi");
//
//        brandService.save(brandEntity);
//        System.out.println("保存成功");

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("name", "xiaomi"));
        list.forEach((item)->{
            System.out.println("item = " + item);
        });
    }

}
