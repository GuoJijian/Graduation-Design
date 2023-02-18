package com.guojijian.pethospital.workbench.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.pethospital.commons.contants.Contants;
import com.guojijian.pethospital.commons.pojo.ReturnObject;
import com.guojijian.pethospital.commons.utils.UUIDUtils;
import com.guojijian.pethospital.settings.pojo.Employee;
import com.guojijian.pethospital.settings.pojo.PetOwner;
import com.guojijian.pethospital.settings.service.EmployeeService;
import com.guojijian.pethospital.workbench.pojo.Appointment;
import com.guojijian.pethospital.workbench.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AppointmentController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping("/workbench/appointment/toIndex")
    public String toIndex(HttpSession session, Model model){
        //获取用户id
        PetOwner p=(PetOwner) session.getAttribute(Contants.SESSION_USER);
        model.addAttribute("userId",p.getPid());

        return "workbench/appointment/index";
    }

    @RequestMapping("/workbench/appointment/queryDoctorForPageByCondition")
    @ResponseBody
    public Object queryDoctorForPageByCondition(String name,String department,String position,String odType,Integer pageNum,Integer pageSize){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("department",department);
        map.put("position",position);
        map.put("odType",odType);
        //查询医生
        PageHelper.startPage(pageNum,pageSize);
        List<Employee> doctorList=employeeService.queryDoctorForPageByCondition(map);
        PageInfo<Employee> page=new PageInfo<>(doctorList,5);
        //封装查询结果
        Map<String,Object> result=new HashMap<>();
        result.put("doctorList",doctorList);
        result.put("page",page);

        return result;
    }

    @RequestMapping("/workbench/appointment/createAppointment")
    @ResponseBody
    public Object createAppointment(Appointment appointment){
        ReturnObject ro=new ReturnObject();
        //封装参数
        appointment.setAid(UUIDUtils.getUUID());
        appointment.setaStatus(Contants.APPOINTMENT_STATUS_ADVANCE);
        try {
            //创建预约信息
            int result=appointmentService.createAppointment(appointment);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("网络繁忙，请稍后再试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("网络繁忙，请稍后再试！");
        }

        return ro;
    }

    @RequestMapping("/workbench/appointment/queryAppointmentByUserId")
    @ResponseBody
    public Object queryAppointmentByUserId(String userId){
        ReturnObject ro=new ReturnObject();
        //查询预约
        Appointment appointment=appointmentService.queryAppointmentByUserId(userId);
        if(appointment!=null){
            //给预约状态设置值
            appointment.setaStatus("预约中");
            ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            ro.setRetObject(appointment);
        }else{
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("无预约记录！");
        }

        return ro;
    }

    @RequestMapping("/workbench/appointment/cancelAppointmentById")
    @ResponseBody
    public Object cancelAppointmentById(Appointment appointment){
        ReturnObject ro=new ReturnObject();
        //封装参数
        appointment.setaStatus(Contants.APPOINTMENT_STATUS_CANCEL);
        try {
            //取消预约
            int result = appointmentService.alterAppointmentStatusById(appointment);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("网络繁忙，请稍后再试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("网络繁忙，请稍后再试！");
        }

        return ro;
    }
}
