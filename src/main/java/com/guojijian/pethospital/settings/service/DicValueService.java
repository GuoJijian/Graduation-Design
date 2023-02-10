package com.guojijian.pethospital.settings.service;

import com.guojijian.pethospital.settings.pojo.DicValue;

import java.util.List;

public interface DicValueService {
    /**
     * 根据typeCode查询DicValue
     */
    List<DicValue> queryDivValueByTypeCode(String typeCode);

    /**
     * 根据id查询Value
     */
    String queryValueById(String id);
}
