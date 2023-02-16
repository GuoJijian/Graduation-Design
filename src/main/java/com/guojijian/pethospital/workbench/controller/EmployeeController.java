package com.guojijian.pethospital.workbench.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.pethospital.commons.contants.Contants;
import com.guojijian.pethospital.commons.pojo.ReturnObject;
import com.guojijian.pethospital.commons.utils.UUIDUtils;
import com.guojijian.pethospital.settings.pojo.DicValue;
import com.guojijian.pethospital.settings.pojo.Employee;
import com.guojijian.pethospital.settings.service.DicValueService;
import com.guojijian.pethospital.settings.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DicValueService dicValueService;

    @RequestMapping("/workbench/administration/employee/toIndex")
    public String toIndex(Model model){
        List<DicValue> positionList=dicValueService.queryDivValueByTypeCode("position");
        List<DicValue> departmentList=dicValueService.queryDivValueByTypeCode("department");

        model.addAttribute("positionList",positionList);
        model.addAttribute("departmentList",departmentList);

        return "workbench/administration/employee/index";
    }

    @RequestMapping("/workbench/administration/employee/queryEmployeeForPageByCondition")
    @ResponseBody
    public Object queryEmployeeForPageByCondition(String name,String department,String position,String odType,Integer pageSize,Integer pageNum){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("department",department);
        map.put("position",position);
        map.put("odType",odType);
        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Employee> employeeList=employeeService.queryEmployeeForPageByCondition(map);
        PageInfo<Employee> page=new PageInfo<>(employeeList,5);
        //封装查询结果，返回响应信息
        Map<String,Object> result=new HashMap<>();
        result.put("employeeList",employeeList);
        result.put("page",page);
        return result;
    }

    @RequestMapping("/workbench/administration/employee/createEmployee")
    @ResponseBody
    public Object createEmployee(Employee employee){
        ReturnObject ro=new ReturnObject();
        //封装参数
        employee.setEid("1-"+ UUIDUtils.getUUID());
        if(dicValueService.queryValueById(employee.getePosition()).contains("护")){
            employee.setGrade(Contants.GRADE_LEVEL_TWO);
        }else if(dicValueService.queryValueById(employee.getePosition()).contains("医")){
            employee.setGrade(Contants.GRADE_LEVEL_THREE);
        }else{
            employee.setGrade(Contants.GRADE_LEVEL_FOUR);
        }
        try {
            //创建员工
            int result=employeeService.createEmployee(employee);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //再次封装返回数据
                employee.seteDepartment(dicValueService.queryValueById(employee.geteDepartment()));
                employee.setePosition(dicValueService.queryValueById(employee.getePosition()));
                employee.setOdType(dicValueService.queryValueById(employee.getOdType()));
                ro.setRetObject(employee);
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

    @RequestMapping("/workbench/administration/employee/querySsTypeByPosition")
    @ResponseBody
    public Object querySsTypeByPosition(String position){
        List<DicValue> ssTypeList=null;
        if(dicValueService.queryValueById(position).contains("医")){
            ssTypeList=dicValueService.queryDivValueByTypeCode("odType");
        }else{
            ssTypeList=dicValueService.queryDivValueByTypeCode("ssType");
        }
        return ssTypeList;
    }

    @RequestMapping("/workbench/administration/employee/alterEmployee")
    @ResponseBody
    public Object alterEmployee(Employee employee){
        ReturnObject ro=new ReturnObject();
        //封装参数
        if(dicValueService.queryValueById(employee.getePosition()).contains("护")){
            employee.setGrade(Contants.GRADE_LEVEL_TWO);
        }else if(dicValueService.queryValueById(employee.getePosition()).contains("医")){
            employee.setGrade(Contants.GRADE_LEVEL_THREE);
        }else{
            employee.setGrade(Contants.GRADE_LEVEL_FOUR);
        }
        try {
            //修改员工信息
            int result=employeeService.alterEmployee(employee);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //再次封装返回数据
                employee.seteDepartment(dicValueService.queryValueById(employee.geteDepartment()));
                employee.setePosition(dicValueService.queryValueById(employee.getePosition()));
                employee.setOdType(dicValueService.queryValueById(employee.getOdType()));
                ro.setRetObject(employee);
            }else{
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("网络繁忙，请稍后再试！");
            }
        }catch(Exception e){
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("网络繁忙，请稍后再试！");
        }

        return ro;
    }

    @RequestMapping("/workbench/administration/employee/queryEmployeeById")
    @ResponseBody
    public Object queryEmployeeById(String id){
        //查询员工
        Employee employee=employeeService.queryEmployeeById(id);

        return employee;
    }

    @RequestMapping("/workbench/administration/employee/dropEmployeeByIds")
    @ResponseBody
    public Object dropEmployeeByIds(String[] ids){
        ReturnObject ro=new ReturnObject();
        try {
            //批量删除
            int result=employeeService.dropEmployeeByIds(ids);
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

    @RequestMapping("/workbench/administration/employee/toDetail")
    public String toDetail(String id,Model model){
        //查询员工详情
        Employee employee=employeeService.queryEmployeeForDetailById(id);
        //将查询结果封装到域对象中
        model.addAttribute("employee",employee);

        return "workbench/administration/employee/detail";
    }
}
