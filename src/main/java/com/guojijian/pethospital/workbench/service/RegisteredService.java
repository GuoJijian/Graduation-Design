package com.guojijian.pethospital.workbench.service;

import com.guojijian.pethospital.workbench.pojo.Registered;

import java.util.List;
import java.util.Map;

public interface RegisteredService {
    /**
     * 根据条件分页查询挂号信息
     */
    List<Registered> queryRegisteredForPageByCondition(Map<String,Object> map);

    /**
     * 添加挂号信息
     */
    int createRegistered(Registered registered);

    /**
     *修改挂号信息
     */
    int alterRegistered(Registered registered);

    /**
     * 根据id查询挂号信息
     */
    Registered queryRegisteredById(String id);

    /**
     * 根据ids批量删除挂号信息
     */
    int dropRegisteredByIds(String[] ids);

    /**
     * 根据id查询挂号信息的详情
     */
    Registered queryRegisteredToDeatilById(String id);

    /**
     * 根据电话号码模糊查询挂号信息至诊疗页面
     */
    List<Registered> queryRegisteredToTreatmentByPhone(String phone);
}
