package com.guojijian.pethospital.workbench.service.impl;

import com.guojijian.pethospital.workbench.mapper.BeautyMapper;
import com.guojijian.pethospital.workbench.pojo.Beauty;
import com.guojijian.pethospital.workbench.service.BeautyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("beautyService")
public class BeautyServiceImpl implements BeautyService {

    @Autowired
    private BeautyMapper beautyMapper;

    @Override
    public List<Beauty> queryBeautyForPageByCondition(Map<String, Object> map) {
        return beautyMapper.queryBeautyForPageByCondition(map);
    }

    @Override
    public int createBeauty(Beauty beauty) {
        return beautyMapper.insertBeauty(beauty);
    }

    @Override
    public int alterBeauty(Beauty beauty) {
        return beautyMapper.updateBeauty(beauty);
    }

    @Override
    public Beauty queryBeautyById(String id) {
        return beautyMapper.selectBeautyById(id);
    }

    @Override
    public int dropBeautyByIds(String[] ids) {
        return beautyMapper.deleteBeautyByIds(ids);
    }

    @Override
    public Beauty queryBeautyForDetailById(String id) {
        return beautyMapper.selectBeautyForDetailById(id);
    }
}
