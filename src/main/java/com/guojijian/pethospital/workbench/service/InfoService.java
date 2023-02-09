package com.guojijian.pethospital.workbench.service;

import com.guojijian.pethospital.workbench.pojo.Info;

import java.util.List;
import java.util.Map;

public interface InfoService {
    /**
     * 创建信息
     */
    int createInfo(Info info);

    /**
     * 根据条件分页查询信息
     */
    List<Info> queryInfoForPageByCondition(Map<String,Object> map);
}
