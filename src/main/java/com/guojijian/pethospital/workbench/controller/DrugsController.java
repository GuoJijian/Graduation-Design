package com.guojijian.pethospital.workbench.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.pethospital.commons.contants.Contants;
import com.guojijian.pethospital.commons.pojo.ReturnObject;
import com.guojijian.pethospital.commons.utils.UUIDUtils;
import com.guojijian.pethospital.workbench.pojo.Drugs;
import com.guojijian.pethospital.workbench.service.DrugsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DrugsController {

    @Autowired
    private DrugsService drugsService;

    @RequestMapping("/workbench/administration/drugs/toIndex")
    public String toIndex(){
        return "workbench/administration/drugs/index";
    }

    @RequestMapping("/workbench/administration/drugs/queryDrugsForPageByCondition")
    @ResponseBody
    public Object queryDrugsForPageByCondition(String name,String stockMin,String stockMax,Integer pageNum,Integer pageSize){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("stockMin",stockMin);
        map.put("stockMax",stockMax);
        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Drugs> drugsList=drugsService.queryDrugsForPageByCondition(map);
        PageInfo<Drugs> page=new PageInfo<>(drugsList,5);
        //封装查询结果，返回响应信息
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("drugsList",drugsList);
        resultMap.put("page",page);

        return resultMap;
    }

    @RequestMapping("/workbench/administration/drugs/createDrugs")
    @ResponseBody
    public Object createDrugs(Drugs drugs){
        ReturnObject ro=new ReturnObject();
        //封装参数
        drugs.setDid(UUIDUtils.getUUID());
        try {
            //创建药品信息
            int result=drugsService.createDrugs(drugs);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                ro.setRetObject(drugs);
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

    @RequestMapping("/workbench/administration/drugs/alterDrugs")
    @ResponseBody
    public Object alterDrugs(Drugs drugs){
        ReturnObject ro=new ReturnObject();
        try {
            //修改药品信息
            int result=drugsService.alterDrugs(drugs);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                ro.setRetObject(drugs);
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

    @RequestMapping("/workbench/administration/drugs/dropDrugsByIds")
    @ResponseBody
    public Object dropDrugsByIds(String[] ids){
        ReturnObject ro=new ReturnObject();
        try{
            //批量删除药品
            int result=drugsService.dropDrugsByIds(ids);
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
