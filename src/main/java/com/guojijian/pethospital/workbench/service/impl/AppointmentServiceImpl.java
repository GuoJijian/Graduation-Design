package com.guojijian.pethospital.workbench.service.impl;

import com.guojijian.pethospital.workbench.mapper.AppointmentMapper;
import com.guojijian.pethospital.workbench.pojo.Appointment;
import com.guojijian.pethospital.workbench.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("appointmentService")
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public int createAppointment(Appointment appointment) {
        return appointmentMapper.insertAppointment(appointment);
    }

    @Override
    public Appointment queryAppointmentByUserId(String userId) {
        return appointmentMapper.selectAppointmentByUserId(userId);
    }

    @Override
    public int alterAppointmentStatusById(Appointment appointment) {
        return appointmentMapper.updateAppointmentStatusById(appointment);
    }
}
