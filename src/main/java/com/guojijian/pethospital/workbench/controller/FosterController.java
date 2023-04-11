package com.guojijian.pethospital.workbench.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.pethospital.commons.contants.Contants;
import com.guojijian.pethospital.commons.pojo.ReturnObject;
import com.guojijian.pethospital.commons.utils.DateUtils;
import com.guojijian.pethospital.commons.utils.UUIDUtils;
import com.guojijian.pethospital.settings.dto.FosterDto;
import com.guojijian.pethospital.settings.pojo.DicValue;
import com.guojijian.pethospital.settings.pojo.Employee;
import com.guojijian.pethospital.settings.pojo.PetOwner;
import com.guojijian.pethospital.settings.service.DicValueService;
import com.guojijian.pethospital.settings.service.PetOwnerService;
import com.guojijian.pethospital.workbench.pojo.Foster;
import com.guojijian.pethospital.workbench.service.FosterService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/workbench/foster")
public class FosterController {

    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private PetOwnerService petOwnerService;
    @Autowired
    private FosterService fosterService;

    @RequestMapping("/toIndex")
    public String toIndex(Model model){

        return "/workbench/foster/index";
    }

    @PostMapping
    @ResponseBody
    public Object createFoster(@RequestBody Foster foster, HttpSession session){
        Employee nurse = (Employee) session.getAttribute(Contants.SESSION_USER);

        //封装参数
        foster.setFid(UUIDUtils.getUUID());
        foster.setfTime(DateUtils.dateFormatDateTime(new Date()));
        foster.setNid(nurse.getEid());
        PetOwner petOwner=petOwnerService.queryPetOwnerById(foster.getPid());
        foster.setpType(petOwner.getpType());
        foster.setpBreed(petOwner.getpBreed());
        foster.setpName(petOwner.getpName());
        //创建寄养信息
        fosterService.createFoster(foster);
        //封装响应结果
        ReturnObject ro=new ReturnObject();
        ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        ro.setMessage("创建成功！");

        return ro;
    }

    @GetMapping("/page")
    @ResponseBody
    public Object queryFosterForPageByCondition(Integer pageNum,Integer pageSize,String phone,String breed,String startDate,String endDate){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("phone",phone);
        map.put("breed",breed);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Foster> fosterList=fosterService.queryFosterForPageByCondition(map);
        List<FosterDto> fosterDtoList=fosterList.stream().map((foster)->{
            FosterDto fosterDto=new FosterDto();
            BeanUtils.copyProperties(foster,fosterDto);
            PetOwner petOwner = petOwnerService.queryPetOwnerById(fosterDto.getPid());
            fosterDto.setName(petOwner.getpUserName());
            fosterDto.setPhone(petOwner.getpPhone());
            return fosterDto;
        }).collect(Collectors.toList());
        PageInfo page=new PageInfo(fosterDtoList,5);
        //封装查询结果，返回响应信息
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("fosterList",fosterDtoList);
        resultMap.put("page",page);

        return resultMap;
    }

    @GetMapping("/update/{id}")
    @ResponseBody
    public Object queryFosterById(@PathVariable String id){

        //查询寄养信息
        Foster foster=fosterService.queryFosterById(id);
        //获取前端页面需要的电话号码
        PetOwner petOwner = petOwnerService.queryPetOwnerById(foster.getPid());
        FosterDto fosterDto=new FosterDto();
        BeanUtils.copyProperties(foster,fosterDto);
        fosterDto.setPhone(petOwner.getpPhone());

        return ReturnObject.success(fosterDto);
    }

    @PostMapping("/update")
    @ResponseBody
    public Object updateFoster(@RequestBody Foster foster){

        fosterService.alterFoster(foster);

        return ReturnObject.success("修改成功！");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object deleteFoster(@RequestBody String[] ids){

        fosterService.dropFosterForBatchByIds(ids);

        return ReturnObject.success("删除成功！");
    }

    @GetMapping("/toDetail")
    public String toDetail(String id,Model model){
        FosterDto fosterDto=new FosterDto();

        Foster foster = fosterService.queryFosterById(id);
        PetOwner petOwner = petOwnerService.queryPetOwnerById(foster.getPid());
        String type = dicValueService.queryValueById(petOwner.getpType());
        String breed = dicValueService.queryValueById(petOwner.getpBreed());

        BeanUtils.copyProperties(foster,fosterDto);

        fosterDto.setName(petOwner.getpUserName());
        fosterDto.setPhone(petOwner.getpPhone());
        fosterDto.setpType(type);
        fosterDto.setpBreed(breed);
        model.addAttribute("foster",fosterDto);

        return "workbench/foster/detail";
    }
}
