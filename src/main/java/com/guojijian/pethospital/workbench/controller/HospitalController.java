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
import com.guojijian.pethospital.settings.service.PetOwnerService;
import com.guojijian.pethospital.workbench.pojo.Hospital;
import com.guojijian.pethospital.workbench.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.krb5.internal.PAEncTSEnc;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private PetOwnerService petOwnerService;
    @Autowired
    private DicValueService dicValueService;

    @RequestMapping("/workbench/hospital/toIndex")
    public String toIndex(Model model){
        //查询页面所需数据
        List<DicValue> addressList=dicValueService.queryDicValueByTypeCode("address");
        //将查询结果封装到request域对象中
        model.addAttribute("addressList",addressList);

        return "workbench/hospital/index";
    }

    @RequestMapping("/workbench/hospital/queryHospitalForPageByCondition")
    @ResponseBody
    public Object queryHospitalForPageByCondition(String name,String phone,String startDate,String endDate,Integer pageNum,Integer pageSize){
        Map<String,Object> map=new HashMap<>();
        //封装参数
        map.put("name",name);
        map.put("phone",phone);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Hospital> hospitalList=hospitalService.queryHospitalForPageByCondition(map);
        PageInfo<Hospital> page=new PageInfo<>(hospitalList,5);
        //封装查询结果，返回响应信息
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("hospitalList",hospitalList);
        resultMap.put("page",page);

        return resultMap;
    }

    @RequestMapping("/workbench/hospital/createHospital")
    @ResponseBody
    public Object createHospital(Hospital hospital,HttpSession session){
        Employee doctor=(Employee)session.getAttribute(Contants.SESSION_USER);
        PetOwner petOwner=petOwnerService.queryPetOwnerById(hospital.getPid());
        ReturnObject ro=new ReturnObject();
        //封装参数
        hospital.setHid(UUIDUtils.getUUID());
        hospital.sethTime(DateUtils.dateFormatDateTime(new Date()));
        hospital.setEid(doctor.getEid());
        hospital.setpName(petOwner.getpName());
        hospital.setpType(petOwner.getpType());
        hospital.setpBreed(petOwner.getpBreed());
        hospital.setpPhone(petOwner.getpPhone());
        try {
            //创建住院信息
            int result=hospitalService.createHospital(hospital);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //二次封装，便于前端数据显示
                hospital.sethAddress(dicValueService.queryValueById(hospital.gethAddress()));
                hospital.setPid(petOwner.getpUserName());
                hospital.setpType(dicValueService.queryValueById(hospital.getpType()));
                hospital.setpBreed(dicValueService.queryValueById(hospital.getpBreed()));
                ro.setRetObject(hospital);
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

    @RequestMapping("/workbench/hospital/queryPetOwnerToHospitalByPhone")
    @ResponseBody
    public Object queryPetOwnerToHospitalByPhone(String phone){
        List<PetOwner> petOwnerList=petOwnerService.queryPetOwnerForFuzzyByPhone(phone);

        return petOwnerList;
    }

    @RequestMapping("/workbench/hospital/alterHospital")
    @ResponseBody
    public Object alterHospital(Hospital hospital,HttpSession session){
        ReturnObject ro=new ReturnObject();
        Employee doctor=(Employee)session.getAttribute(Contants.SESSION_USER);
        PetOwner petOwner=petOwnerService.queryPetOwnerById(hospital.getPid());
        //封装参数
        hospital.sethTime(DateUtils.dateFormatDateTime(new Date()));
        hospital.setEid(doctor.getEid());
        hospital.setpName(petOwner.getpName());
        hospital.setpType(petOwner.getpType());
        hospital.setpBreed(petOwner.getpBreed());
        hospital.setpPhone(petOwner.getpPhone());
        try {
            //修改住院信息
            int result=hospitalService.alterHospital(hospital);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //二次封装，便于前端数据显示
                hospital.sethAddress(dicValueService.queryValueById(hospital.gethAddress()));
                hospital.setPid(petOwner.getpUserName());
                hospital.setpType(dicValueService.queryValueById(hospital.getpType()));
                hospital.setpBreed(dicValueService.queryValueById(hospital.getpBreed()));
                ro.setRetObject(hospital);
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

    @RequestMapping("/workbench/hospital/queryHospitalById")
    @ResponseBody
    public Object queryHospitalById(String id){
        //查询住院信息
        Hospital hospital=hospitalService.queryHospitalById(id);

        return hospital;
    }

    @RequestMapping("/workbench/hospital/dropHospitalByIds")
    @ResponseBody
    public Object dropHospitalByIds(String[] ids){
        ReturnObject ro=new ReturnObject();
        try {
            //批量删除
            int result=hospitalService.dropHospitalByIds(ids);
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
