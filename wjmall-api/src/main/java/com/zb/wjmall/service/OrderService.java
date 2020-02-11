package com.zb.wjmall.service;

import com.zb.wjmall.bean.OrderInfo;

public interface OrderService {
    String genTradeCode(String userId);

    boolean checkTradeCode(String tradeCode, String userId);

    void saveOrder(OrderInfo orderInfo);
}
