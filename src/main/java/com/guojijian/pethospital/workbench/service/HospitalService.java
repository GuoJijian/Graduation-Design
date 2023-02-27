package com.guojijian.pethospital.workbench.service;

import com.guojijian.pethospital.workbench.pojo.Hospital;

import java.util.List;
import java.util.Map;

public interface HospitalService {
    /**
     * 根据条件分页查询住院信息
     */
    List<Hospital> queryHospitalForPageByCondition(Map<String,Object> map);

    /**
     * 创建住院信息
     */
    int createHospital(Hospital hospital);

    /**
     * 修改住院信息
     */
    int alterHospital(Hospital hospital);

    /**
     * 根据id查询住院信息
     */
    Hospital queryHospitalById(String id);

    /**
     * 根据ids批量删除住院信息
     */
    int dropHospitalByIds(String[] ids);
}
