package com.guojijian.pethospital.workbench.service;

import com.guojijian.pethospital.workbench.pojo.Appointment;

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
}
