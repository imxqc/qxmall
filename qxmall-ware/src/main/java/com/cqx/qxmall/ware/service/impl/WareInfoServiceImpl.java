package com.cqx.qxmall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.cqx.common.utils.R;
import com.cqx.qxmall.ware.feign.MemberFeignService;
import com.cqx.qxmall.ware.vo.FareVo;
import com.cqx.qxmall.ware.vo.MemberAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqx.common.utils.PageUtils;
import com.cqx.common.utils.Query;

import com.cqx.qxmall.ware.dao.WareInfoDao;
import com.cqx.qxmall.ware.entity.WareInfoEntity;
import com.cqx.qxmall.ware.service.WareInfoService;
import org.springframework.util.StringUtils;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Autowired
    private MemberFeignService memberFeignService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> wareInfoEntityQueryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wareInfoEntityQueryWrapper.eq("id", key).or()
                    .like("name", key)
                    .or().like("address", key)
                    .or().like("areacode", key);
        }

        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                wareInfoEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public FareVo getFare(Long addrId) {
        FareVo fareVo = new FareVo();
        R info = memberFeignService.addrInfo(addrId);
        MemberAddressVo addressVo = info.getData("memberReceiveAddress", new TypeReference<MemberAddressVo>() {});
        if(addressVo != null){
            String phone = addressVo.getPhone();
            BigDecimal decimal = new BigDecimal(phone.substring(phone.length() - 1,phone.length()));

            fareVo.setMemberAddressVo(addressVo);
            fareVo.setFare(decimal);
            return fareVo;
        }else{

        }

        return null;
    }

}