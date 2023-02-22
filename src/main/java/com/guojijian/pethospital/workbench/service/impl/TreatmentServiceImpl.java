package com.guojijian.pethospital.workbench.service.impl;

import com.guojijian.pethospital.workbench.mapper.TreatmentMapper;
import com.guojijian.pethospital.workbench.pojo.Treatment;
import com.guojijian.pethospital.workbench.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("treatmentService")
public class TreatmentServiceImpl implements TreatmentService {

    @Autowired
    private TreatmentMapper treatmentMapper;

    @Override
    public List<Treatment> queryTreatmentForPageByCondition(Map<String, Object> map) {
        return treatmentMapper.selectTreatmentForPageByCondition(map);
    }

    @Override
    public int createTreatment(Treatment treatment) {
        return treatmentMapper.insertTreatment(treatment);
    }

    @Override
    public int alterTreatment(Treatment treatment) {
        return treatmentMapper.updateTreatment(treatment);
    }

    @Override
    public Treatment queryTreatmentById(String id) {
        return treatmentMapper.selectTreatmentById(id);
    }

    @Override
    public int dropTreatmentByIds(String[] ids) {
        return treatmentMapper.deleteTreatmentByIds(ids);
    }

    @Override
    public Treatment queryTreatmentForDetailById(String id) {
        return treatmentMapper.selectTreatmentForDetailById(id);
    }
}
