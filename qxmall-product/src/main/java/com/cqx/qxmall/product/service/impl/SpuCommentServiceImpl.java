package com.cqx.qxmall.product.service.impl;

import com.cqx.common.utils.PageUtils;
import com.cqx.common.utils.Query;
import com.cqx.qxmall.product.dao.SpuCommentDao;
import com.cqx.qxmall.product.entity.SpuCommentEntity;
import com.cqx.qxmall.product.service.SpuCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("spuCommentService")
public class SpuCommentServiceImpl extends ServiceImpl<SpuCommentDao, SpuCommentEntity> implements SpuCommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuCommentEntity> page = this.page(
                new Query<SpuCommentEntity>().getPage(params),
                new QueryWrapper<SpuCommentEntity>()
        );

        return new PageUtils(page);
    }

}