package com.cqx.qxmall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.cqx.common.valid.AddGroup;
import com.cqx.common.valid.UpdateGroup;
import com.cqx.common.valid.UpdateStatusGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cqx.qxmall.product.entity.BrandEntity;
import com.cqx.qxmall.product.service.BrandService;
import com.cqx.common.utils.PageUtils;
import com.cqx.common.utils.R;

import javax.validation.Valid;


/**
 * Ʒ��
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 20:44:25
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand /**,BindingResult result**/) {
//        if(result.hasErrors()){
//            Map<String,String> map = new HashMap<>();
//            //获取错误信息
//            result.getFieldErrors().forEach((item)->{
//                //错误信息
//                String message = item.getDefaultMessage();
//                //错误属性名
//                String field = item.getField();
//                //放入map
//                map.put(field,message);
//            });
//
//            return R.error(400,"提交的数据不合法").put("data",map);
//        }else{
//            brandService.save(brand);
//        }

        brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@Validated(UpdateGroup.class) @RequestBody BrandEntity brand) {
        brandService.updateDetail(brand);
        return R.ok();
    }

//    /**
//     * 修改状态
//     */
//    @RequestMapping("/update/status")
//    public R updateStatus(@Validated(UpdateStatusGroup.class) @RequestBody BrandEntity brand){
//        boolean b = brandService.updateById(brand);
//        System.out.println("b = " + b);
//
//        return R.ok();
//    }

    /**
     * 修改状态
     */
    @RequestMapping("/update/status")
    public R updateStatus(@Validated(UpdateStatusGroup.class) @RequestBody BrandEntity brand) {
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
