package com.guojijian.pethospital.workbench.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.pethospital.commons.contants.Contants;
import com.guojijian.pethospital.commons.pojo.ReturnObject;
import com.guojijian.pethospital.commons.utils.DateUtils;
import com.guojijian.pethospital.commons.utils.UUIDUtils;
import com.guojijian.pethospital.settings.pojo.DicValue;
import com.guojijian.pethospital.settings.pojo.Employee;
import com.guojijian.pethospital.settings.pojo.PetOwner;
import com.guojijian.pethospital.settings.service.DicValueService;
import com.guojijian.pethospital.settings.service.EmployeeService;
import com.guojijian.pethospital.settings.service.PetOwnerService;
import com.guojijian.pethospital.workbench.pojo.Registered;
import com.guojijian.pethospital.workbench.service.RegisteredService;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class RegisteredController {

    @Autowired
    private RegisteredService registeredService;
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PetOwnerService petOwnerService;

    @RequestMapping("/workbench/registered/toIndex")
    public String toIndex(Model model){
        List<DicValue> departmentList=dicValueService.queryDicValueByTypeCode("department");
        //将查询结果保存到request作用域中
        model.addAttribute("departmentList",departmentList);

        return "workbench/registered/index";
    }

    @RequestMapping("/workbench/registered/queryRegisteredForPageByCondition")
    @ResponseBody
    public Object queryRegisteredForPageByCondition(String department,String phone,String startDate,String endDate,Integer pageSize,Integer pageNum){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("department",department);
        map.put("phone",phone);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Registered> registeredList=registeredService.queryRegisteredForPageByCondition(map);
        PageInfo<Registered> page=new PageInfo<>(registeredList,5);
        //封装查询结果，返回响应信息
        Map<String,Object> result=new HashMap<>();
        result.put("registeredList",registeredList);
        result.put("page",page);

        return result;
    }

    @RequestMapping("/workbench/registered/createRegistered")
    @ResponseBody
    public Object createRegistered(Registered registered,HttpSession session){
        Employee nurse=(Employee)session.getAttribute(Contants.SESSION_USER);
        ReturnObject ro=new ReturnObject();
        //封装参数
        registered.setRid(UUIDUtils.getUUID());
        registered.setrDate(DateUtils.dateFormatDate(new Date()));
        //判断门诊类型
        //通过医生id查询医生
        Employee doctor=employeeService.queryEmployeeById(registered.getDoctor());
        String odType=dicValueService.queryValueById(doctor.getOdType());
        if("普通门诊".equals(odType)){
            registered.setrPrice(Contants.ODTYPE_COMMON);
        }else if("保健门诊".equals(odType)){
            registered.setrPrice(Contants.ODTYPE_HEALTH);
        }else{
            registered.setrPrice(Contants.ODTYPE_EXPERT);
        }
        registered.setrDepartment(doctor.geteDepartment());
        //通过用户id查询用户
        PetOwner petOwner=petOwnerService.queryPetOwnerById(registered.getPid());
        registered.setpPhone(petOwner.getpPhone());
        registered.setpName(petOwner.getpName());
        registered.setpBreed(petOwner.getpBreed());
        registered.setpType(petOwner.getpType());
        registered.setEid(nurse.getEid());
        try{
            //创建挂号信息
            int result=registeredService.createRegistered(registered);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //二次封装，便于前端显示
                registered.setPid(petOwner.getpUserName());
                registered.setrDepartment(dicValueService.queryValueById(registered.getrDepartment()));
                registered.setpType(dicValueService.queryValueById(registered.getpType()));
                registered.setEid(nurse.geteName());
                ro.setRetObject(registered);
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

    @RequestMapping("/workbench/registered/queryDoctorByDepartment")
    @ResponseBody
    public Object queryDoctorByDepartment(String department){
        List<Employee> doctorList=employeeService.queryDoctorByDepartment(department);
        return doctorList;
    }

    @RequestMapping("/workbench/registered/queryPetOwnerForFuzzyByPhone")
    @ResponseBody
    public Object queryPetOwnerForFuzzyByPhone(String phone){
        List<PetOwner> petOwnerList=petOwnerService.queryPetOwnerForFuzzyByPhone(phone);
        return petOwnerList;
    }

    @RequestMapping("/workbench/registered/alterRegistered")
    @ResponseBody
    public Object alterRegistered(Registered registered,HttpSession session){
        Employee nurse=(Employee)session.getAttribute(Contants.SESSION_USER);
        ReturnObject ro=new ReturnObject();
        //判断门诊类型
        //通过医生id查询医生
        Employee doctor=employeeService.queryEmployeeById(registered.getDoctor());
        String odType=dicValueService.queryValueById(doctor.getOdType());
        if("普通门诊".equals(odType)){
            registered.setrPrice(Contants.ODTYPE_COMMON);
        }else if("保健门诊".equals(odType)){
            registered.setrPrice(Contants.ODTYPE_HEALTH);
        }else{
            registered.setrPrice(Contants.ODTYPE_EXPERT);
        }
        registered.setrDepartment(doctor.geteDepartment());
        //通过用户id查询用户
        PetOwner petOwner=petOwnerService.queryPetOwnerById(registered.getPid());
        registered.setpPhone(petOwner.getpPhone());
        registered.setpName(petOwner.getpName());
        registered.setpBreed(petOwner.getpBreed());
        registered.setpType(petOwner.getpType());
        registered.setEid(nurse.getEid());
        try{
            //修改挂号信息
            int result=registeredService.alterRegistered(registered);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //二次封装，便于前端显示
                registered.setPid(petOwner.getpUserName());
                registered.setrDepartment(dicValueService.queryValueById(registered.getrDepartment()));
                registered.setpType(dicValueService.queryValueById(registered.getpType()));
                registered.setEid(nurse.geteName());
                ro.setRetObject(registered);
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

    @RequestMapping("/workbench/registered/queryRegisteredById")
    @ResponseBody
    public Object queryRegisteredById(String id){
        //查询挂号信息
        Registered registered=registeredService.queryRegisteredById(id);
        //根据doctorId查询医生信息
        Employee doctor=employeeService.queryEmployeeById(registered.getDoctor());
        //根据pid查询用户信息
        PetOwner petOwner=petOwnerService.queryPetOwnerById(registered.getPid());
        //封装挂号信息，便于前端显示
        registered.setDoctorName(doctor.geteName());
        registered.setrDepartment(dicValueService.queryValueById(registered.getrDepartment()));
        registered.setDoctorPosition(dicValueService.queryValueById(doctor.getePosition()));
        registered.setDoctorOdType(dicValueService.queryValueById(doctor.getOdType()));
        registered.setpName(petOwner.getpUserName());
        registered.setpType(dicValueService.queryValueById(registered.getpType()));
        registered.setpBreed(dicValueService.queryValueById(registered.getpBreed()));

        return registered;
    }

    @RequestMapping("/workbench/registered/dropRegisteredByIds")
    @ResponseBody
    public Object dropRegisteredByIds(String[] ids){
        ReturnObject ro=new ReturnObject();
        try {
            //批量删除挂号信息
            int result=registeredService.dropRegisteredByIds(ids);
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

    @RequestMapping("/workbench/registered/toDetail")
    public String toDetail(String id,Model model){
        Registered registered=registeredService.queryRegisteredToDeatilById(id);
        model.addAttribute("registered",registered);

        return "workbench/registered/detail";
    }
}
