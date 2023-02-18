package com.guojijian.pethospital.settings.service;

import com.guojijian.pethospital.settings.pojo.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    //通过id查询用户
    Employee queryEmployeeById(String id);

    /**
     *根据条件分页查询员工
     * @param map
     * @return
     */
    List<Employee> queryEmployeeForPageByCondition(Map<String,Object> map);

    /**
     * 创建新员工
     */
    int createEmployee(Employee employee);

    /**
     * 修改员工信息
     */
    int alterEmployee(Employee employee);

    /**
     * 批量删除员工
     */
    int dropEmployeeByIds(String[] ids);

    /**
     * 根据id查询员工的详细信息
     */
    Employee queryEmployeeForDetailById(String id);

    /**
     * 根据条件分页查询医生
     */
    List<Employee> queryDoctorForPageByCondition(Map<String,Object> map);
}
