package com.guojijian.pethospital.workbench.service.impl;

import com.guojijian.pethospital.workbench.mapper.DrugsMapper;
import com.guojijian.pethospital.workbench.pojo.Drugs;
import com.guojijian.pethospital.workbench.service.DrugsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("drugsService")
public class DrugsServiceImpl implements DrugsService {

    @Autowired
    private DrugsMapper drugsMapper;

    @Override
    public List<Drugs> queryDrugsForPageByCondition(Map<String, Object> map) {
        return drugsMapper.selectDrugsForPageByCondition(map);
    }

    @Override
    public int createDrugs(Drugs drugs) {
        return drugsMapper.insertDrugs(drugs);
    }

    @Override
    public int alterDrugs(Drugs drugs) {
        return drugsMapper.updateDrugs(drugs);
    }

    @Override
    public int dropDrugsByIds(String[] ids) {
        return drugsMapper.deleteDrugsByIds(ids);
    }
}
