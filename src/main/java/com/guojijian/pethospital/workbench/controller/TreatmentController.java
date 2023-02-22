package com.guojijian.pethospital.workbench.controller;

import com.github.pagehelper.Page;
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
import com.guojijian.pethospital.workbench.pojo.Drugs;
import com.guojijian.pethospital.workbench.pojo.Registered;
import com.guojijian.pethospital.workbench.pojo.Treatment;
import com.guojijian.pethospital.workbench.service.DrugsService;
import com.guojijian.pethospital.workbench.service.RegisteredService;
import com.guojijian.pethospital.workbench.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class TreatmentController {

    @Autowired
    private TreatmentService treatmentService;
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DrugsService drugsService;
    @Autowired
    private RegisteredService registeredService;
    @Autowired
    private PetOwnerService petOwnerService;

    @RequestMapping("/workbench/treatment/toIndex")
    public String toIndex(Model model){
        List<DicValue> diseaseList=dicValueService.queryDivValueByTypeCode("disease");
        List<Drugs> drugsList=drugsService.queryDrugsAll();
        model.addAttribute("diseaseList",diseaseList);
        model.addAttribute("drugsList",drugsList);

        return "workbench/treatment/index";
    }

    @RequestMapping("/workbench/treatment/queryTreatmentForPageByCondition")
    @ResponseBody
    public Object queryTreatmentForPageByCondition(String name,String disease,String startDate,String endDate,Integer pageNum,Integer pageSize){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("disease",disease);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        //分页查询诊疗信息
        PageHelper.startPage(pageNum,pageSize);
        List<Treatment> treatmentList=treatmentService.queryTreatmentForPageByCondition(map);
        PageInfo<Treatment> page=new PageInfo<>(treatmentList,5);
        //封装查询结果，返回响应信息
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("treatmentList",treatmentList);
        resultMap.put("page",page);

        return resultMap;
    }


    @RequestMapping("/workbench/treatment/createTreatment")
    @ResponseBody
    public Object createTreatment(Treatment treatment, HttpSession session){
        ReturnObject ro=new ReturnObject();
        Employee doctor=(Employee) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        treatment.setTid(UUIDUtils.getUUID());
        treatment.settTime(DateUtils.dateFormatDateTime(new Date()));
        treatment.setEid(doctor.getEid());
        try {
            //创建诊疗信息
            int result=treatmentService.createTreatment(treatment);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //二次封装，便于前端显示数据
                treatment.setEid(employeeService.queryEmployeeById(treatment.getEid()).geteName());
                treatment.settDisease(dicValueService.queryValueById(treatment.gettDisease()));
                if(treatment.getDid()!=null && !"".equals(treatment.getDid())) {
                    treatment.setDid(drugsService.queryDrugsById(treatment.getDid()).getdName());
                }
                ro.setRetObject(treatment);
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

    @RequestMapping("/workbench/treatment/queryRegisteredToTreatmentByPhone")
    @ResponseBody
    public Object queryRegisteredToTreatmentByPhone(String phone){
        List<Registered> registeredList=registeredService.queryRegisteredToTreatmentByPhone(phone);
        return registeredList;
    }

    @RequestMapping("/workbench/treatment/alterTreatment")
    @ResponseBody
    public Object alterTreatment(Treatment treatment,HttpSession session){
        Employee doctor=(Employee)session.getAttribute(Contants.SESSION_USER);
        ReturnObject ro=new ReturnObject();
        //封装参数
        treatment.setEid(doctor.getEid());
        treatment.settTime(DateUtils.dateFormatDateTime(new Date()));
        try {
            //修改诊疗信息
            int result=treatmentService.alterTreatment(treatment);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //二次封装，便于前端页面显示数据
                treatment.setEid(doctor.geteName());
                treatment.settDisease(dicValueService.queryValueById(treatment.gettDisease()));
                if(!"".equals(treatment.getDid())){
                    treatment.setDid(drugsService.queryDrugsById(treatment.getDid()).getdName());
                }
                ro.setRetObject(treatment);
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

    @RequestMapping("/workbench/treatment/queryTreatmentById")
    @ResponseBody
    public Object queryTreatmentById(String id){
        //查询诊疗信息
        Treatment treatment=treatmentService.queryTreatmentById(id);
        //封装参数
        treatment.setpPhone(registeredService.queryRegisteredById(treatment.getRid()).getpPhone());

        return treatment;
    }

    @RequestMapping("/workbench/treatment/dropTreatmentByIds")
    @ResponseBody
    public Object dropTreatmentByIds(String[] ids){
        ReturnObject ro=new ReturnObject();
        try{
            int result=treatmentService.dropTreatmentByIds(ids);
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

    @RequestMapping("/workbench/treatment/toDetail")
    public String toDetail(String id,Model model){
        //查询诊疗详细信息
        Treatment treatment=treatmentService.queryTreatmentForDetailById(id);
        Registered registered=registeredService.queryRegisteredById(treatment.getRid());
        //封装参数，便于页面显示
        registered.setPid(petOwnerService.queryPetOwnerById(registered.getPid()).getpUserName());
        registered.setpType(dicValueService.queryValueById(registered.getpType()));
        registered.setpBreed(dicValueService.queryValueById(registered.getpBreed()));

        //将查询结果封装到request域对象中
        model.addAttribute("treatment",treatment);
        model.addAttribute("registered",registered);

        return "workbench/treatment/detail";
    }
}
