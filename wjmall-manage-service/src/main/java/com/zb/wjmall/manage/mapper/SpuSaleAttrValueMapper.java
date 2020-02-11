package com.zb.wjmall.manage.mapper;


import com.zb.wjmall.bean.SkuInfo;
import com.zb.wjmall.bean.SpuSaleAttr;
import com.zb.wjmall.bean.SpuSaleAttrValue;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SpuSaleAttrValueMapper extends Mapper<SpuSaleAttrValue> {

    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(Map<String, String> map);

    List<SkuInfo> selectSkuSaleAttrValueListBySpu(@Param("spuId") String spuId);

}
