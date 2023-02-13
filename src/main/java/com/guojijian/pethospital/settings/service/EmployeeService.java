package com.guojijian.pethospital.settings.service;

import com.guojijian.pethospital.settings.pojo.Employee;

public interface EmployeeService {
    //通过id查询用户
    Employee queryEmployeeById(String id);
}
