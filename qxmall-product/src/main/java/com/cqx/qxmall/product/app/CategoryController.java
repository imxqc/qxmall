package com.cqx.qxmall.product.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cqx.qxmall.product.entity.CategoryEntity;
import com.cqx.qxmall.product.service.CategoryService;
import com.cqx.common.utils.PageUtils;
import com.cqx.common.utils.R;


/**
 * ��Ʒ��������
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 20:44:25
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 返回所有分类以及子类,以树状结构显示
     *
     * @return
     */
    @RequestMapping("/list/tree")
    public R list() {
        List<CategoryEntity> tree = categoryService.listWithTree();

        return R.ok().put("data", tree);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateCascade(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update/sort")
    public R updateSort(@RequestBody CategoryEntity[] category) {
        categoryService.updateBatchById(Arrays.asList(category));

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds) {
//		categoryService.removeByIds(Arrays.asList(catIds));

        //按照条件逻辑删除
        categoryService.removeMenuByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
