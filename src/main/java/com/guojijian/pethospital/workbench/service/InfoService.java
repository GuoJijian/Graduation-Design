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

    /**
     * 根据id查询信息
     */
    Info queryInfoById(String id);

    /**
     * 修改信息
     */
    int alterInfo(Info info);

    /**
     * 根据ids批量删除信息
     */
    int dropInfoByIds(String[] ids);

    /**
     * 根据id查询详细信息
     */
    Info queryInfoForDetailById(String id);
}
