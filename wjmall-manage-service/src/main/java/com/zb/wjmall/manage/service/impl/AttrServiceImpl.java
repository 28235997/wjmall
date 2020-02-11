package com.zb.wjmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zb.wjmall.bean.BaseAttrInfo;
import com.zb.wjmall.bean.BaseAttrValue;
import com.zb.wjmall.manage.mapper.BaseAttrInfoMapper;
import com.zb.wjmall.manage.mapper.BaseAttrValueMapper;
import com.zb.wjmall.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;

    @Override
    public List<BaseAttrInfo> getAttrList(String catalog3Id) {
        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        List<BaseAttrInfo> baseAttrInfos = baseAttrInfoMapper.select(baseAttrInfo);
        for (BaseAttrInfo baseAttrInfo1 : baseAttrInfos) {
            BaseAttrValue baseAttrValue = new BaseAttrValue();
            baseAttrValue.setAttrId(baseAttrInfo1.getId());
            List<BaseAttrValue> baseAttrValues = baseAttrValueMapper.select(baseAttrValue);
            baseAttrInfo1.setAttrValueList(baseAttrValues);
        }
        return baseAttrInfos;
    }

    @Override
    public void saveAttr(BaseAttrInfo baseAttrInfo) {
        String id = baseAttrInfo.getId();
        if(StringUtils.isBlank(id)){
            baseAttrInfoMapper.insertSelective(baseAttrInfo);   //insert insertSelective 是否将null插入数据库
            List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
            for (BaseAttrValue baseAttrValue:
             attrValueList) {
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insert(baseAttrValue);
            }
        }else{
            BaseAttrValue baseAttrValue = new BaseAttrValue();
            baseAttrValue.setAttrId(baseAttrInfo.getId());
            baseAttrValueMapper.delete(baseAttrValue);
            List<BaseAttrValue> attrValueList2 = baseAttrInfo.getAttrValueList();
            for (BaseAttrValue baseAttrValue2: attrValueList2) {
                baseAttrValue2.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insert(baseAttrValue2);
            }
        }
    }

    @Override
    public List<BaseAttrValue> getAttrValue(String attrId) {
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);
        return baseAttrValueMapper.select(baseAttrValue);
    }

    @Override
    public void deleteAttr(String attrId) {
        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setId(attrId);
        baseAttrInfoMapper.delete(baseAttrInfo);
    }

    @Override
    public List<BaseAttrInfo> getAttrListByCtg3Id(String ctg3Id) {
        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(ctg3Id);
        List<BaseAttrInfo> baseAttrInfos = baseAttrInfoMapper.select(baseAttrInfo);
        for (BaseAttrInfo attrInfo :
                baseAttrInfos) {
            BaseAttrValue baseAttrValue = new BaseAttrValue();
            baseAttrValue.setAttrId(attrInfo.getId());
            List<BaseAttrValue> baseAttrValues = baseAttrValueMapper.select(baseAttrValue);
            attrInfo.setAttrValueList(baseAttrValues);
        }
        return baseAttrInfos;
    }

    @Override
    public List<BaseAttrInfo> getAttrListByValueIds(Set<String> valueIds) {
        String join = StringUtils.join(valueIds, ",");
        List<BaseAttrInfo> baseAttrInfos = baseAttrValueMapper.selectAttrListByValueIds(join);
        return baseAttrInfos;
    }
}
