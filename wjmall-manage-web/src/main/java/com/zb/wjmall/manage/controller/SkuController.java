package com.zb.wjmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zb.wjmall.bean.SkuInfo;
import com.zb.wjmall.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class SkuController {

    @Reference
    SkuService skuService;


    @RequestMapping("getSkuListBySpuId")
    @ResponseBody
    public List<SkuInfo> getSkuListBySpuId(String spuId) {
        return skuService.getSkuListBySpuId(spuId);
    }

    @RequestMapping("saveSkuInfo")
    @ResponseBody
    public String saveSku(@RequestBody SkuInfo skuInfo) {
        skuService.saveSku(skuInfo);
        return "success";
    }




}
