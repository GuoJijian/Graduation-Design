package com.guojijian.pethospital.workbench.service.impl;

import com.guojijian.pethospital.workbench.mapper.FosterMapper;
import com.guojijian.pethospital.workbench.pojo.Foster;
import com.guojijian.pethospital.workbench.service.FosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("fosterService")
public class FosterServiceImpl implements FosterService {

    @Autowired
    private FosterMapper fosterMapper;

    @Override
    public int createFoster(Foster foster) {
        return fosterMapper.insertFoster(foster);
    }

    @Override
    public List<Foster> queryFosterForPageByCondition(Map<String, Object> map) {
        return fosterMapper.queryFosterForPageByCondition(map);
    }

    @Override
    public int alterFoster(Foster foster) {
        return fosterMapper.updateFoster(foster);
    }

    @Override
    public Foster queryFosterById(String id) {
        return fosterMapper.selectFosterById(id);
    }

    @Override
    public int dropFosterForBatchByIds(String[] ids) {
        return fosterMapper.deleteFosterForBatchByIds(ids);
    }
}
