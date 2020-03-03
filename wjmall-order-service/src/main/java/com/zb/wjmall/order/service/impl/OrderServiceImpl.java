package com.zb.wjmall.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zb.wjmall.bean.OrderDetail;
import com.zb.wjmall.bean.OrderInfo;
import com.zb.wjmall.order.mapper.OrderDetailMapper;
import com.zb.wjmall.order.mapper.OrderInfoMapper;
import com.zb.wjmall.service.OrderService;
import com.zb.wjmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Override
    public String genTradeCode(String userId) {

        String key = "user:" + userId + ":tradeCode";
        String val = UUID.randomUUID().toString();

        Jedis jedis = redisUtil.getJedis();
        jedis.setex(key, 60 * 30, val);
        jedis.close();

        return val;
    }

    @Override
    public boolean checkTradeCode(String tradeCode, String userId) {
        boolean bool = false;

        String key = "user:" + userId + ":tradeCode";
        Jedis jedis = redisUtil.getJedis();
        String val = jedis.get(key);

        if (tradeCode.equals(val)) {
            bool = true;
            jedis.del(key);
        }

        return bool;
    }

    @Override
    public void saveOrder(OrderInfo orderInfo) {

        orderInfoMapper.insertSelective(orderInfo);
        String orderId = orderInfo.getId();

        List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList) {
            orderDetail.setOrderId(orderId);
            orderDetailMapper.insertSelective(orderDetail);
        }

    }

    @Override
    public OrderInfo getOrderByUserId(String userId) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(userId);
        orderInfo.setOrderStatus("0");
        OrderInfo orderInfo1 = orderInfoMapper.selectOne(orderInfo);
        return orderInfo1;
    }
}
