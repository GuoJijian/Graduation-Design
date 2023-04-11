package com.guojijian.pethospital.workbench.service;

import com.guojijian.pethospital.workbench.pojo.Appointment;

import java.util.List;
import java.util.Map;

public interface AppointmentService {
    /**
     * 创建预约
     */
    int createAppointment(Appointment appointment);

    /**
     * 根据用户id查找预约
     */
    Appointment queryAppointmentByUserId(String userId);

    /**
     * 根据id修改预约状态
     */
    int alterAppointmentStatusById(Appointment appointment);

    /**
     * 根据条件分页查询预约信息
     */
    List<Appointment> queryAppointmentForPageByCondition(Map<String,Object> map);
}
