package com.cqx.qxmall.product.service.impl;

import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.cqx.qxmall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqx.common.utils.PageUtils;
import com.cqx.common.utils.Query;

import com.cqx.qxmall.product.dao.CategoryDao;
import com.cqx.qxmall.product.entity.CategoryEntity;
import com.cqx.qxmall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 返回所有分类以及子类,以树状结构显示
     *
     * @return
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        //获取所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //获取父分类,设置子分类,根据sort进行排序
        List<CategoryEntity> level1 = entities.stream()
                .filter(item -> item.getParentCid() == 0)
                .map((menu) -> {
                    menu.setChildren(getChildren(menu, entities));
                    return menu;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());
        //返回树状结构的分类
        return level1;
    }


    /**
     * 根据父分类par获取其所有的子分类并返回
     *
     * @param par
     * @param all
     * @return
     */
    public List<CategoryEntity> getChildren(CategoryEntity par, List<CategoryEntity> all) {
        //根据父分类的cat_id获取对应分类, 对应分类也要递归获取对应子分类
        List<CategoryEntity> children = all.stream()
                .filter(item -> item.getParentCid() == par.getCatId())
                .map((menu) -> {
                    menu.setChildren(getChildren(menu, all));
                    return menu;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());
        //返回父分类par的子分类集合
        return children;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //todo 校验该菜单是否在其他地方被引用

        //逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

    /**
     * 该节点的完整路径 [父,子,孙]
     *
     * @param catelogId
     * @return
     */
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> fullPath = new ArrayList<>();

        List<Long> parentPath = findParentPath(catelogId, fullPath);

        //顺序改为 [父,子,孙]
        Collections.reverse(parentPath);

        //将list以long数组方式返回
        return fullPath.toArray(new Long[fullPath.size()]);
    }

    @Override
    @Transactional
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    /**
     * 获取该节点的完整路径
     *
     * @param catelogId
     * @param path
     * @return
     */
    public List<Long> findParentPath(Long catelogId, List<Long> path) {
        path.add(catelogId);

        CategoryEntity categoryEntity = this.getById(catelogId);

        if (categoryEntity.getParentCid() != 0) {
            findParentPath(categoryEntity.getParentCid(), path);
        }

        return path;
    }
}