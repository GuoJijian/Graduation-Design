package com.guojijian.pethospital.workbench.service;

import com.guojijian.pethospital.workbench.pojo.Drugs;

import java.util.List;
import java.util.Map;

public interface DrugsService {
    /**
     * 根据条件分页查询药品
     */
    List<Drugs> queryDrugsForPageByCondition(Map<String,Object> map);

    /**
     * 创建药品信息
     */
    int createDrugs(Drugs drugs);

    /**
     * 修改药品信息
     */
    int alterDrugs(Drugs drugs);

    /**
     * 根据ids批量删除药品信息
     */
    int dropDrugsByIds(String[] ids);
}
