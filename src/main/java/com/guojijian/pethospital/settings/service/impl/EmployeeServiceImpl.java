package com.guojijian.pethospital.settings.service.impl;

import com.guojijian.pethospital.settings.mapper.EmployeeMapper;
import com.guojijian.pethospital.settings.pojo.Employee;
import com.guojijian.pethospital.settings.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee queryEmployeeById(String id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Employee> queryEmployeeForPageByCondition(Map<String, Object> map) {
        return employeeMapper.selectEmployeeForPageByCondition(map);
    }

    @Override
    public int createEmployee(Employee employee) {
        return employeeMapper.insertEmployee(employee);
    }

    @Override
    public int alterEmployee(Employee employee) {
        return employeeMapper.updateEmployee(employee);
    }

    @Override
    public int dropEmployeeByIds(String[] ids) {
        return employeeMapper.deleteEmployeeByIds(ids);
    }

    @Override
    public Employee queryEmployeeForDetailById(String id) {
        return employeeMapper.selectEmployeeForDetailById(id);
    }
}
