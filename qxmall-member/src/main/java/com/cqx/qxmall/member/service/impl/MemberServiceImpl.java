package com.cqx.qxmall.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqx.common.utils.HttpUtils;
import com.cqx.common.vo.SocialUser;
import com.cqx.qxmall.member.dao.MemberLevelDao;
import com.cqx.qxmall.member.entity.MemberLevelEntity;
import com.cqx.qxmall.member.exception.PhoneExistException;
import com.cqx.qxmall.member.exception.UserNameExistException;
import com.cqx.qxmall.member.vo.MemberLoginVo;
import com.cqx.qxmall.member.vo.UserRegisterVo;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqx.common.utils.PageUtils;
import com.cqx.common.utils.Query;

import com.cqx.qxmall.member.dao.MemberDao;
import com.cqx.qxmall.member.entity.MemberEntity;
import com.cqx.qxmall.member.service.MemberService;

import javax.annotation.Resource;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {
    @Resource
    private MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(UserRegisterVo userRegisterVo) throws PhoneExistException, UserNameExistException {
        MemberEntity entity = new MemberEntity();
        // 设置默认等级
        MemberLevelEntity memberLevelEntity = memberLevelDao.getDefaultLevel();
        entity.setLevelId(memberLevelEntity.getId());

        // 检查手机号 用户名是否唯一
        checkPhone(userRegisterVo.getPhone());
        checkUserName(userRegisterVo.getUserName());

        entity.setMobile(userRegisterVo.getPhone());
        entity.setUsername(userRegisterVo.getUserName());
        entity.setNickname(userRegisterVo.getUserName());

        // 密码要加密存储
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        entity.setPassword(bCryptPasswordEncoder.encode(userRegisterVo.getPassword()));

        // 其他的默认信息
//        entity.setCreateTime(new Date());
//        entity.setStatus(0);
//        entity.setNickname(userRegisterVo.getUserName());
//        entity.setGender(1);

        //存入数据
        baseMapper.insert(entity);
    }

    @Override
    public void checkPhone(String phone) throws PhoneExistException {
        if (this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone)) > 0) {
            throw new PhoneExistException();
        }
    }

    @Override
    public void checkUserName(String username) throws UserNameExistException {
        if (this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", username)) > 0) {
            throw new UserNameExistException();
        }
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        String loginacct = vo.getLoginacct();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        // 去数据库查询
        MemberEntity entity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("username", loginacct).or().eq("mobile", loginacct));
        if (entity == null) {
            // 登录失败
            return null;
        } else {
            // 前面传一个明文密码 后面传一个编码后的密码
            boolean matches = bCryptPasswordEncoder.matches(vo.getPassword(), entity.getPassword());
            if (matches) {
                entity.setPassword(null);
                return entity;
            } else {
                return null;
            }
        }
    }

    /**
     * 查询是否注册过 未注册则注册
     * 注册了则更新令牌,令牌时间
     *
     * @param socialUser
     * @return
     */
    @Override
    public MemberEntity login(SocialUser socialUser) {
        // 微博的uid
        String uid = socialUser.getUid();
        // 1.判断社交用户登录过系统
        MemberDao dao = this.baseMapper;
        MemberEntity entity = dao.selectOne(new QueryWrapper<MemberEntity>().eq("social_uid", uid));

        if (entity != null) {
            // 说明这个用户注册过, 更新令牌,令牌时间
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setId(entity.getId());
            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setExpiresIn(socialUser.getExpires_in());
            // 更新
            dao.updateById(memberEntity);
            entity.setAccessToken(socialUser.getAccess_token());
            entity.setExpiresIn(socialUser.getExpires_in());
            return entity;
        } else {
            MemberEntity regist = new MemberEntity();

            // 获取用户信息可能网络问题获取不到,所以用trycatch包裹,保证程序运行
            try {
                Map<String, String> map = new HashMap<>();
                map.put("access_token", socialUser.getAccess_token());

                HttpResponse response = HttpUtils.doGet("https://gitee.com", "/api/v5/user", "get", new HashMap<>(), map);

                if (response.getStatusLine().getStatusCode() == 200) {
                    String s = EntityUtils.toString(response.getEntity());
                    JSONObject object = JSON.parseObject(s);
                    String name = object.getString("name");
                    regist.setNickname("dear_" + name);
                    regist.setUsername(name);
                } else {
                    System.out.println("接口获取信息失败");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            MemberLevelEntity memberLevelEntity = memberLevelDao.getDefaultLevel();
            regist.setLevelId(memberLevelEntity.getId());
            regist.setSocialUid(socialUser.getUid());
            regist.setAccessToken(socialUser.getAccess_token());
            regist.setExpiresIn(socialUser.getExpires_in());
            dao.insert(regist);

            return regist;
        }
    }

}