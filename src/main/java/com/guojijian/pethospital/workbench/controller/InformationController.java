package com.guojijian.pethospital.workbench.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.pethospital.commons.contants.Contants;
import com.guojijian.pethospital.commons.pojo.ReturnObject;
import com.guojijian.pethospital.commons.utils.DateUtils;
import com.guojijian.pethospital.commons.utils.UUIDUtils;
import com.guojijian.pethospital.settings.pojo.Employee;
import com.guojijian.pethospital.workbench.pojo.Info;
import com.guojijian.pethospital.workbench.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class InformationController {

    @Autowired
    private InfoService infoService;

    @RequestMapping("/workbench/information/toIndex")
    public String toIndex(){
        return "workbench/information/index";
    }

    @RequestMapping("/workbench/information/toOperate")
    public String toOperate(){
        return "workbench/information/operate";
    }

    @RequestMapping("/workbench/information/createInfo")
    @ResponseBody
    public Object createInfo(Info info, HttpSession session){
        ReturnObject ro=new ReturnObject();
        //获取参数
        Employee employee=(Employee)session.getAttribute(Contants.SESSION_USER);
        //封装参数
        info.setiId(UUIDUtils.getUUID());
        info.setiTime(DateUtils.dateFormatDateTime(new Date()));
        info.setEid(employee.getEid());
        try {
            //创建消息
            int result=infoService.createInfo(info);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                ro.setRetObject(info);
            }else{
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("系统繁忙，请稍后再试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("系统繁忙，请稍后再试！");
        }
        return ro;
    }

    @RequestMapping("/workbench/information/queryInfoForPageByCondition")
    @ResponseBody
    public Object queryInfoForPageByCondition(String iTitle,String iInfotype,String startDate,String endDate,Integer pageSize,Integer pageNum){
        Map<String,Object> map=new HashMap<>();
        //封装参数
        map.put("iTitle",iTitle);
        map.put("iInfotype",iInfotype);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Info> infoList=infoService.queryInfoForPageByCondition(map);
        PageInfo<Info> page=new PageInfo<>(infoList,5);

        return page;
    }
}
