package com.zb.wjmall.service;

import com.zb.wjmall.bean.SkuLsInfo;
import com.zb.wjmall.bean.SkuLsParam;

import java.util.List;

public interface SearchService {

    List<SkuLsInfo> search(SkuLsParam skuLsParam);
}
