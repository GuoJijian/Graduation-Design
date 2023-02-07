package com.guojijian.pethospital.settings.controller;

import com.guojijian.pethospital.commons.contants.Contants;
import com.guojijian.pethospital.commons.pojo.ReturnObject;
import com.guojijian.pethospital.commons.utils.UUIDUtils;
import com.guojijian.pethospital.settings.pojo.Employee;
import com.guojijian.pethospital.settings.pojo.PetOwner;
import com.guojijian.pethospital.settings.service.PetOwnerService;
import com.guojijian.pethospital.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PetOwnerService petOwnerService;

    @RequestMapping("/settings/qx/user/toLogin")
    public String toLogin(){
        return "/settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login")
    @ResponseBody
    public Object login(PetOwner petOwner, HttpSession session){
        ReturnObject ro=new ReturnObject();
        //验证账号密码
        Object obj=userService.queryLoginInfoByActAndPwd(petOwner);
        //处理查询结果并封装响应信息
        if(obj==null){
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("账号或密码错误，请重试！");
        }else{
            ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            //判断查询结果类型并转换
            if(obj instanceof PetOwner){
                petOwner=(PetOwner)obj;
                session.setAttribute(Contants.SESSION_USER,petOwner);
            }else{
                Employee e=(Employee) obj;
                session.setAttribute(Contants.SESSION_USER,e);
            }
        }


        return ro;
    }

    @RequestMapping("/settings/qx/user/toRegister")
    public String toRegister(){
        return "settings/qx/user/register";
    }

    @RequestMapping("/settings/qx/user/register")
    @ResponseBody
    public Object register(PetOwner petOwner){
        ReturnObject ro=new ReturnObject();
        //封装参数
        petOwner.setPid(UUIDUtils.getUUID());
        petOwner.setGrade(Contants.GRADE_LEVEL_ONE);
        //判断用户是否存在
        Object obj=userService.queryLoginInfoByActAndPwd(petOwner);
        if(obj!=null){
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("用户已存在，请重试！");
            return ro;
        }
        try {
            //注册用户
            int result=petOwnerService.createPetOwner(petOwner);
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
