package com.guojijian.pethospital.settings.service.impl;

import com.guojijian.pethospital.settings.mapper.DicValueMapper;
import com.guojijian.pethospital.settings.pojo.DicValue;
import com.guojijian.pethospital.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dicValueService")
public class DicValueServiceImpl implements DicValueService {

    @Autowired
    private DicValueMapper dicValueMapper;

    @Override
    public List<DicValue> queryDicValueByTypeCode(String typeCode) {
        return dicValueMapper.selectDicValueByTypeCode(typeCode);
    }

    @Override
    public String queryValueById(String id) {
        return dicValueMapper.selectValueById(id);
    }
}
