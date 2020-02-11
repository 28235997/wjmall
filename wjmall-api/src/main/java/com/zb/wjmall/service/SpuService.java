package com.zb.wjmall.service;

import com.zb.wjmall.bean.SpuInfo;
import com.zb.wjmall.bean.SpuSaleAttr;

import java.util.List;
import java.util.Map;

public interface SpuService {
    List<SpuInfo> spuList(String catalog3Id);

    List<com.zb.wjmall.bean.BaseSaleAttr> baseSaleAttrList();

    void saveSpu(SpuInfo spuInfo);

    List<com.zb.wjmall.bean.SpuImage> getSpuImageList(String spuId);

    List<SpuSaleAttr> getSpuSaleAttrList(String spuId);

    void deleteSpu(String spuId);

    List<SpuSaleAttr> getSpuSaleAttrListBySpuId(String spuId);

    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Map<String, String> idMap);

    List<com.zb.wjmall.bean.SkuInfo> getSkuSaleAttrValueListBySpu(String spuId);
}
