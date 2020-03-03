package com.zb.wjmall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zb.wjmall.annotation.LoginRequire;
import com.zb.wjmall.bean.CartInfo;
import com.zb.wjmall.bean.OrderDetail;
import com.zb.wjmall.bean.OrderInfo;
import com.zb.wjmall.bean.UserAddress;
import com.zb.wjmall.bean.enums.PaymentWay;
import com.zb.wjmall.service.CartService;
import com.zb.wjmall.service.OrderService;
import com.zb.wjmall.service.SkuService;
import com.zb.wjmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Reference
    CartService cartService;
    @Reference
    UserService userService;
    @Reference
    OrderService orderService;
    @Reference
    SkuService skuService;


    //支付页
    @LoginRequire(ifNeedSuccess = true)
    @RequestMapping("index")
    public String payIndex(HttpServletRequest request,ModelMap map){
        String userId = (String) request.getAttribute("userId");
        OrderInfo orderInfo = orderService.getOrderByUserId(userId);
        map.put("orderId",orderInfo.getOutTradeNo());
        map.put("totalAmount",orderInfo.getTotalAmount());
        return "index";
    }

    @LoginRequire(ifNeedSuccess = true)
    @RequestMapping("submitOrder")
    public String submitOrder(HttpServletRequest request, OrderInfo orderInfo2, ModelMap map, String traderCode) {
        String userId = (String) request.getAttribute("userId");
        //比较交易码
        boolean bTrade = orderService.checkTradeCode(traderCode, userId);
        //订单对象
        OrderInfo orderInfo = new OrderInfo();
        List<OrderDetail> orderDetails = new ArrayList<>();

        //执行提交订单业务
        if (bTrade) {

            //获取购物车中被选中的商品数据
            List<CartInfo> cartInfos = cartService.getCartCacheByChecked(userId);

            //生成订单信息
            for (CartInfo cartInfo : cartInfos) {
                OrderDetail orderDetail = new OrderDetail();
                String skuId = cartInfo.getSkuId();
                BigDecimal skuPrice = cartInfo.getSkuPrice();
                //验价
                boolean bPrice = skuService.checkPrice(skuPrice, skuId);
                //验库存

                if (bPrice) {
                    orderDetail.setSkuName(cartInfo.getSkuName());
                    orderDetail.setSkuId(cartInfo.getSkuId());
                    orderDetail.setOrderPrice(cartInfo.getCartPrice());
                    orderDetail.setImgUrl(cartInfo.getImgUrl());
                    orderDetail.setSkuNum(cartInfo.getSkuNum());
                    orderDetails.add(orderDetail);
                } else {
                    map.put("errMsg", "订单中的商品价格（库存）发生了变化，请重新选择订单");
                    return "tradeFail";
                }

            }
            orderInfo.setOrderDetailList(orderDetails);

            //封装订单信息
            orderInfo.setProcessStatus("订单未支付");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1);
            orderInfo.setExpireTime(calendar.getTime());
            orderInfo.setOrderStatus("0");

            orderInfo.setConsignee(orderInfo2.getConsignee());
            //外部订单号
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime = sdf.format(new Date());
            String outTradeNo = "Zb" + currentTime + System.currentTimeMillis();
            orderInfo.setOutTradeNo(outTradeNo);
            orderInfo.setPaymentWay(PaymentWay.ONLINE);
            orderInfo.setUserId(userId);
            orderInfo.setTotalAmount(getTotalPrice(cartInfos));
            orderInfo.setOrderComment("订单");
            orderInfo.setDeliveryAddress(orderInfo2.getDeliveryAddress());
            orderInfo.setCreateTime(new Date());
            String tel = "123123";
            orderInfo.setConsigneeTel(tel);
            

            orderService.saveOrder(orderInfo);

            //删除购物车中提交的商品信息，同步缓存
            cartService.deleteCartById(cartInfos);



        } else {
            return "tradeFail";
        }

        return "redirect:/index";
    }

    @LoginRequire(ifNeedSuccess = true)
    @RequestMapping("toTrade")
    public String toTrade(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        String userId = (String) request.getAttribute("userId");

        //将选中的购物车对象转化为订单对象
        List<CartInfo> cartInfos = cartService.getCartCacheByChecked(userId);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartInfo cartInfo : cartInfos) {
            OrderDetail orderDetail = new OrderDetail();
            //将购物车对象转化为订单对象
            orderDetail.setImgUrl(cartInfo.getImgUrl());
            orderDetail.setOrderPrice(cartInfo.getCartPrice());
            orderDetail.setSkuId(cartInfo.getSkuId());
            orderDetail.setSkuName(cartInfo.getSkuName());
            orderDetail.setSkuNum(cartInfo.getSkuNum());
            orderDetails.add(orderDetail);
        }

        //查询用户收货地址，让用户选择
        List<UserAddress> userAddresses = userService.getUserAddressList(userId);

        //生成交易码
        String traderCode = orderService.genTradeCode(userId);

        map.put("userAddressList", userAddresses);
        map.put("orderDetailList", orderDetails);
        map.put("totalAmount", getTotalPrice(cartInfos));
        map.put("traderCode",traderCode);
        return "trade";
    }

    private BigDecimal getTotalPrice(List<CartInfo> cartInfos) {
        BigDecimal totalPrice = new BigDecimal("0");
        for (CartInfo cartInfo : cartInfos) {
            totalPrice = totalPrice.add(cartInfo.getCartPrice());
        }

        return totalPrice;
    }

}
