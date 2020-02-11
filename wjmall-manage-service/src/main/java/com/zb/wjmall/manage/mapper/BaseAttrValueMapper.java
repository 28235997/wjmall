package com.zb.wjmall.manage.mapper;

import com.zb.wjmall.bean.BaseAttrInfo;
import com.zb.wjmall.bean.BaseAttrValue;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BaseAttrValueMapper extends Mapper<BaseAttrValue> {
    List<BaseAttrInfo> selectAttrListByValueIds(@Param("ids") String join);
}
