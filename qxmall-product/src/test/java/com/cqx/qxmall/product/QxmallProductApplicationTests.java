package com.cqx.qxmall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqx.qxmall.product.entity.BrandEntity;
import com.cqx.qxmall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QxmallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void test() {
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setName("xiaomi");
//
//        brandService.save(brandEntity);
//        System.out.println("保存成功");

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("name", "xiaomi"));
        list.forEach((item) -> {
            System.out.println("item = " + item);
        });
    }

    @Test
    public void testRedisson(){
        System.out.println(redissonClient);
    }
}
