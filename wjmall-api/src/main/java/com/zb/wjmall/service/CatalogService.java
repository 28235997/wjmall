package com.zb.wjmall.service;

import com.zb.wjmall.bean.BaseCatalog1;
import com.zb.wjmall.bean.BaseCatalog2;
import com.zb.wjmall.bean.BaseCatalog3;

import java.util.List;

public interface CatalogService {
    List<BaseCatalog1> getCatalog1();

    List<BaseCatalog2> getCatalog2(String catalog1Id);

    List<BaseCatalog3> getCatalog3(String catalog2Id);
}
