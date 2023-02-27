package com.guojijian.pethospital.workbench.service.impl;

import com.guojijian.pethospital.workbench.mapper.HospitalMapper;
import com.guojijian.pethospital.workbench.pojo.Hospital;
import com.guojijian.pethospital.workbench.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalMapper hospitalMapper;

    @Override
    public List<Hospital> queryHospitalForPageByCondition(Map<String, Object> map) {
        return hospitalMapper.selectHospitalForPageByCondition(map);
    }

    @Override
    public int createHospital(Hospital hospital) {
        return hospitalMapper.insertHospital(hospital);
    }

    @Override
    public int alterHospital(Hospital hospital) {
        return hospitalMapper.updateHospital(hospital);
    }

    @Override
    public Hospital queryHospitalById(String id) {
        return hospitalMapper.selectHospitalById(id);
    }

    @Override
    public int dropHospitalByIds(String[] ids) {
        return hospitalMapper.deleteHospitalByIds(ids);
    }
}
