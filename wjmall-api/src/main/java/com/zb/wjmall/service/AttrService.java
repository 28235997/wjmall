package com.zb.wjmall.service;

import com.zb.wjmall.bean.BaseAttrInfo;
import com.zb.wjmall.bean.BaseAttrValue;

import java.util.List;
import java.util.Set;

public interface AttrService {

    List<BaseAttrInfo> getAttrList(String catalog3Id);

    void saveAttr(BaseAttrInfo baseAttrInfo);

    List<BaseAttrValue> getAttrValue(String attrName);

    void deleteAttr(String attrId);

    List<BaseAttrInfo> getAttrListByCtg3Id(String ctg3Id);

    List<BaseAttrInfo> getAttrListByValueIds(Set<String> valueIds);
}
