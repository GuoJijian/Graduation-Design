package com.guojijian.pethospital.workbench.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.pethospital.commons.contants.Contants;
import com.guojijian.pethospital.commons.pojo.ReturnObject;
import com.guojijian.pethospital.commons.utils.DateUtils;
import com.guojijian.pethospital.commons.utils.UUIDUtils;
import com.guojijian.pethospital.settings.pojo.DicValue;
import com.guojijian.pethospital.settings.pojo.Employee;
import com.guojijian.pethospital.settings.service.DicValueService;
import com.guojijian.pethospital.workbench.pojo.Beauty;
import com.guojijian.pethospital.workbench.pojo.Registered;
import com.guojijian.pethospital.workbench.service.BeautyService;
import com.guojijian.pethospital.workbench.service.RegisteredService;
import com.sun.tracing.dtrace.ArgsAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BeautyController {

    @Autowired
    private BeautyService beautyService;
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private RegisteredService registeredService;

    @RequestMapping("/workbench/beauty/toIndex")
    public String toIndex(Model model){
        List<DicValue> diseaseList=dicValueService.queryDivValueByTypeCode("beautyDisease");
        model.addAttribute("diseaseList",diseaseList);

        return "/workbench/beauty/index";
    }

    @RequestMapping("/workbench/beauty/queryBeautyForPageByCondition")
    @ResponseBody
    public Object queryBeautyForPageByCondition(String name,String disease,String startDate,String endDate,Integer pageSize,Integer pageNum){
        Map<String,Object> map=new HashMap<>();
        //????????????
        map.put("name",name);
        map.put("disease",disease);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        //????????????
        PageHelper.startPage(pageNum,pageSize);
        List<Beauty> beautyList=beautyService.queryBeautyForPageByCondition(map);
        PageInfo<Beauty> page=new PageInfo<>(beautyList,5);
        //??????????????????
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("beautyList",beautyList);
        resultMap.put("page",page);

        return resultMap;
    }

    @RequestMapping("/workbench/beauty/createBeauty")
    @ResponseBody
    public Object createBeauty(Beauty beauty, HttpSession session){
        Employee doctor=(Employee) session.getAttribute(Contants.SESSION_USER);
        ReturnObject ro=new ReturnObject();
        //????????????
        beauty.setBid(UUIDUtils.getUUID());
        beauty.setbTime(DateUtils.dateFormatDateTime(new Date()));
        beauty.setEid(doctor.getEid());
        try {
            //??????????????????
            int result=beautyService.createBeauty(beauty);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //???????????????????????????????????????
                beauty.setEid(doctor.geteName());
                beauty.setbDisease(dicValueService.queryValueById(beauty.getbDisease()));
                ro.setRetObject(beauty);
            }else{
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("?????????????????????????????????");
            }
        }catch (Exception e){
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("?????????????????????????????????");
        }

        return ro;
    }

    @RequestMapping("/workbench/beauty/alterBeauty")
    @ResponseBody
    public Object alterBeauty(Beauty beauty,HttpSession session){
        Employee doctor=(Employee) session.getAttribute(Contants.SESSION_USER);
        ReturnObject ro=new ReturnObject();
        //????????????
        beauty.setbTime(DateUtils.dateFormatDateTime(new Date()));
        beauty.setEid(doctor.getEid());
        try {
            //??????????????????
            int result = beautyService.alterBeauty(beauty);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //???????????????????????????????????????
                beauty.setEid(doctor.geteName());
                beauty.setbDisease(dicValueService.queryValueById(beauty.getbDisease()));
                ro.setRetObject(beauty);
            }else{
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("?????????????????????????????????");
            }
        }catch (Exception e){
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("?????????????????????????????????");
        }

        return ro;
    }

    @RequestMapping("/workbench/beauty/queryBeautyById")
    @ResponseBody
    public Object queryBeautyById(String id){
        //??????id??????????????????
        Beauty beauty=beautyService.queryBeautyById(id);
        //????????????
        beauty.setbPhone(registeredService.queryRegisteredById(beauty.getRid()).getpPhone());

        return beauty;
    }

    @RequestMapping("/workbench/beauty/dropBeautyByIds")
    @ResponseBody
    public Object dropBeautyByIds(String[] ids){
        ReturnObject ro=new ReturnObject();
        try {
            //????????????
            int result=beautyService.dropBeautyByIds(ids);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("?????????????????????????????????");
            }
        }catch (Exception e){
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("?????????????????????????????????");
        }

        return ro;
    }

    @RequestMapping("/workbench/beauty/toDetail")
    public String toDetail(String id,Model model){
        //????????????
        Beauty beauty=beautyService.queryBeautyForDetailById(id);
        Registered registered=registeredService.queryRegisteredToDeatilById(beauty.getRid());
        //????????????????????????request????????????
        model.addAttribute("beauty",beauty);
        model.addAttribute("registered",registered);

        return "workbench/beauty/detail";
    }
}
