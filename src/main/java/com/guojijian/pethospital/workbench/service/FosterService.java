package com.guojijian.pethospital.workbench.service;

import com.guojijian.pethospital.workbench.pojo.Foster;

import java.util.List;
import java.util.Map;

public interface FosterService {

    /**
     * 创建寄养信息
     * @param foster
     * @return
     */
    int createFoster(Foster foster);

    /**
     * 分页查询寄养信息
     * @param map
     * @return
     */
    List<Foster> queryFosterForPageByCondition(Map<String,Object> map);

    /**
     * 修改寄养信息
     * @param foster
     * @return
     */
    int alterFoster(Foster foster);

    /**
     * 根据id查询寄养信息
     * @param id
     * @return
     */
    Foster queryFosterById(String id);

    /**
     * 根据ids批量删除寄养信息
     * @param ids
     * @return
     */
    int dropFosterForBatchByIds(String[] ids);
}
