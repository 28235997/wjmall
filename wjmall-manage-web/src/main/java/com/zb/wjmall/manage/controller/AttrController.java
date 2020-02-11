package com.zb.wjmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zb.wjmall.bean.BaseAttrInfo;
import com.zb.wjmall.bean.BaseAttrValue;
import com.zb.wjmall.service.AttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class AttrController {

    @Reference
    AttrService attrService;

    //显示属性列表
    @RequestMapping("getAttrInfoList")
    @ResponseBody
    public List<BaseAttrInfo> getAttrList(String catalog3Id) {
        List<BaseAttrInfo> baseAttrInfos = attrService.getAttrList(catalog3Id);
        return baseAttrInfos;
    }

    //将数据插入到baseAttrInfo表和baseAttrValue表中
    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttr(@RequestBody BaseAttrInfo baseAttrInfo) {
        attrService.saveAttr(baseAttrInfo);
        return "success";
    }

    //显示属性值列表
    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<BaseAttrValue> getAttrValue(String attrId) {
        List<BaseAttrValue> baseAttrValues = attrService.getAttrValue(attrId);
        return baseAttrValues;
    }

    //删除选中的属性列表行
    @RequestMapping("deleteAttr")
    @ResponseBody
    public String deleteAttr(String attrId) {
        attrService.deleteAttr(attrId);
        return "success";
    }

    @RequestMapping("getAttrListByCtg3Id")
    @ResponseBody
    public List<BaseAttrInfo> getAttrListByCtg3Id(String ctg3Id) {
        return attrService.getAttrListByCtg3Id(ctg3Id);
    }


}
