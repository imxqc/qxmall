package com.cqx.qxmall.product.web;

import com.cqx.qxmall.product.entity.CategoryEntity;
import com.cqx.qxmall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/22 22:33
 */
public class IndexController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping({"/", "index", "/index.html"})
    public String indexPage(Model model) {
        // 获取一级分类所有缓存
        List<CategoryEntity> categorys = categoryService.getLevel1Categorys();

        //将一级缓存作为属性存入model
        model.addAttribute("categorys", categorys);


        //视图解析器会进行拼串
        // 从 classpath:/templates + 返回值 + .html
        return "index";
    }
}
