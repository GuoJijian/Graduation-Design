package com.guojijian.pethospital.workbench.service.impl;

import com.guojijian.pethospital.workbench.mapper.InfoMapper;
import com.guojijian.pethospital.workbench.pojo.Info;
import com.guojijian.pethospital.workbench.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("infoService")
public class InfoServiceImpl implements InfoService {

    @Autowired
    private InfoMapper infoMapper;

    @Override
    public int createInfo(Info info) {
        return infoMapper.insertInfo(info);
    }

    @Override
    public List<Info> queryInfoForPageByCondition(Map<String, Object> map) {
        return infoMapper.selectInfoForPageByCondition(map);
    }
}
