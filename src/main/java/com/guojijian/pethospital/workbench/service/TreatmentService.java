package com.guojijian.pethospital.workbench.service;

import com.guojijian.pethospital.workbench.pojo.Treatment;

import java.util.List;
import java.util.Map;

public interface TreatmentService {
    /**
     * 根据条件分页查询诊疗信息
     */
    List<Treatment> queryTreatmentForPageByCondition(Map<String,Object> map);

    /**
     * 添加诊疗信息
     */
    int createTreatment(Treatment treatment);

    /**
     * 修改诊疗信息
     */
    int alterTreatment(Treatment treatment);

    /**
     * 根据id查询诊疗信息
     */
    Treatment queryTreatmentById(String id);

    /**
     * 根据ids批量删除诊疗信息
     */
    int dropTreatmentByIds(String[] ids);

    /**
     * 根据id查询诊疗的详细信息
     */
    Treatment queryTreatmentForDetailById(String id);
}
