package com.zb.wjmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.zb.wjmall.bean.UserAddress;
import com.zb.wjmall.bean.UserInfo;
import com.zb.wjmall.service.UserService;
import com.zb.wjmall.user.mapper.UserAddressMapper;
import com.zb.wjmall.user.mapper.UserInfoMapper;
import com.zb.wjmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;
//这是dubbo的service注解
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserAddressMapper userAddressMapper;
    @Autowired
    RedisUtil redisUtil;


    @Override
    public List<UserInfo> userInfoList() {
        return userInfoMapper.selectAll();
    }

    @Override
    public UserInfo login(UserInfo userInfo) {
        UserInfo user = userInfoMapper.selectOne(userInfo);
        if (user != null) {
            //同步缓存
            Jedis jedis = redisUtil.getJedis();
            jedis.set("user:" + user.getId() + ":info", JSON.toJSONString(user));
            jedis.close();
        }
        return user;
    }

    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        List<UserAddress> userAddressList = userAddressMapper.select(userAddress);


        return userAddressList;
    }
}
