package com.guojijian.pethospital.settings.service.impl;

import com.guojijian.pethospital.settings.mapper.EmployeeMapper;
import com.guojijian.pethospital.settings.pojo.Employee;
import com.guojijian.pethospital.settings.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee queryEmployeeById(String id) {
        return employeeMapper.selectByPrimaryKey(id);
    }
}
