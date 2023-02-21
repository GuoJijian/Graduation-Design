package com.guojijian.pethospital.workbench.service.impl;

import com.guojijian.pethospital.workbench.mapper.RegisteredMapper;
import com.guojijian.pethospital.workbench.pojo.Registered;
import com.guojijian.pethospital.workbench.service.RegisteredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("registeredService")
public class RegisteredServiceImpl implements RegisteredService {

    @Autowired
    private RegisteredMapper registeredMapper;

    @Override
    public List<Registered> queryRegisteredForPageByCondition(Map<String, Object> map) {
        return registeredMapper.selectRegisteredForPageByCondition(map);
    }

    @Override
    public int createRegistered(Registered registered) {
        return registeredMapper.insertRegistered(registered);
    }

    @Override
    public int alterRegistered(Registered registered) {
        return registeredMapper.updateRegistered(registered);
    }

    @Override
    public Registered queryRegisteredById(String id) {
        return registeredMapper.selectRegisteredById(id);
    }

    @Override
    public int dropRegisteredByIds(String[] ids) {
        return registeredMapper.deleteRegisteredByIds(ids);
    }

    @Override
    public Registered queryRegisteredToDeatilById(String id) {
        return registeredMapper.selectRegisteredToDetailById(id);
    }
}
