package com.guojijian.pethospital.workbench.service;

import com.guojijian.pethospital.workbench.pojo.Beauty;

import java.util.List;
import java.util.Map;

public interface BeautyService {
    /**
     * 根据条件分页查询美容信息
     */
    List<Beauty> queryBeautyForPageByCondition(Map<String,Object> map);

    /**
     * 添加美容信息
     */
    int createBeauty(Beauty beauty);

    /**
     * 修改美容信息
     */
    int alterBeauty(Beauty beauty);

    /**
     * 根据id查询美容信息
     */
    Beauty queryBeautyById(String id);

    /**
     * 根据ids批量删除美容信息
     */
    int dropBeautyByIds(String[] ids);

    /**
     * 根据id查询美容详细信息
     */
    Beauty queryBeautyForDetailById(String id);
}
