package com.cqx.qxmall.product.web;

import com.cqx.qxmall.product.entity.CategoryEntity;
import com.cqx.qxmall.product.service.CategoryService;
import com.cqx.qxmall.product.vo.Catelog2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/22 22:33
 */
@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    RedissonClient redisson;


    /**
     * 获取一级分类缓存存入model
     *
     * @param model
     * @return
     */
    @RequestMapping({"/", "index", "/index.html"})
    public String indexPage(Model model) {
        // 获取一级分类所有缓存
        List<CategoryEntity> categorys = categoryService.getLevel1Categorys();

        //将一级缓存作为属性存入model
        model.addAttribute("categorys", categorys);


        //视图解析器会进行拼串(未加responsebody)
        // 从 classpath:/templates 路径下找
        // 返回值 + .html
        return "index";
    }


    /**
     * 获取二三级分类信息  以map<一级分类id,二级vo>形式返回
     * @param model
     * @return
     */
    @ResponseBody
    @GetMapping("index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatelogJson(Model model) {
        Map<String, List<Catelog2Vo>> map = categoryService.getCatelogJson();

        return map;
    }


    @GetMapping("/hello")
    public String hello(){
        //获取锁 并为锁取名(后续根据锁名进行可重入锁的判断)
        RLock lock = redisson.getLock("myLock");
        //进行加锁
        lock.lock();

        RSemaphore s = redisson.getSemaphore("s");


        try {
            System.out.println("成功加锁,线程.."+ Thread.currentThread().getId());
        } catch (Exception e) {

        } finally {
            System.out.println("成功加锁,线程.."+ Thread.currentThread().getId());
            //释放锁
            lock.unlock();
        }

        return "hello";
    }
}
