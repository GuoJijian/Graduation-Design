package com.guojijian.pethospital.settings.controller;

import com.guojijian.pethospital.commons.contants.Contants;
import com.guojijian.pethospital.commons.pojo.ReturnObject;
import com.guojijian.pethospital.commons.utils.DateUtils;
import com.guojijian.pethospital.commons.utils.UUIDUtils;
import com.guojijian.pethospital.settings.pojo.Employee;
import com.guojijian.pethospital.settings.pojo.PetOwner;
import com.guojijian.pethospital.settings.service.PetOwnerService;
import com.guojijian.pethospital.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("/settings/qx/user/getLoginCookie")
    @ResponseBody
    public Object getLoginCookie(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        Map<String,String> map=new HashMap<>();
        //判断cookie数组是否有值
        if(cookies.length>0){
            for (Cookie cookie:cookies){
                if(cookie.getName().equals("loginAct")){
                    map.put("loginAct",cookie.getValue());
                }else if(cookie.getName().equals("loginPwd")){
                    map.put("loginPwd",cookie.getValue());
                }
            }
        }
        if(map.containsKey("loginAct")){
            map.put("isRomPwd","true");
        }
        return map;
    }

    @RequestMapping("/settings/qx/user/login")
    @ResponseBody
    public Object login(PetOwner petOwner, String isRomPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        ReturnObject ro=new ReturnObject();
        //验证账号密码
        Object obj=userService.queryLoginInfoByActAndPwd(petOwner);
        //处理查询结果并封装响应信息
        if(obj==null){
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("账号或密码错误，请重试！");
        }else{
            //判断查询结果类型并转换
            if(obj instanceof PetOwner){
                petOwner=(PetOwner)obj;
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                session.setAttribute(Contants.SESSION_USER,petOwner);
            }else{
                Employee e=(Employee) obj;
                //判断账号是否过期
                if(DateUtils.dateFormatDateTime(new Date()).compareTo(e.geteExpireTime())>0){
                    ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                    ro.setMessage("账号已过期！");
                    return ro;
                }else if("0".equals(e.geteLockState())){//判断账号是否锁定
                    ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                    ro.setMessage("账号已锁定！");
                    return ro;
                }else if(!e.geteAllowIps().contains(request.getRemoteAddr())){//判断是否在安全ip登录
                    ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                    ro.setMessage("用户IP不合法！");
                    return ro;
                }else{
                    ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                    session.setAttribute(Contants.SESSION_USER,e);
                }

                //判断是否记住密码
                if("true".equals(isRomPwd)){
                    Cookie loginAct=new Cookie("loginAct",petOwner.getpUserName());
                    loginAct.setMaxAge(60*60*24*10);
                    response.addCookie(loginAct);

                    Cookie loginPwd=new Cookie("loginPwd",petOwner.getpPassword());
                    loginPwd.setMaxAge(60*60*24*10);
                    response.addCookie(loginPwd);
                }else{
                    Cookie loginAct=new Cookie("loginAct","0");
                    loginAct.setMaxAge(0);
                    response.addCookie(loginAct);

                    Cookie loginPwd=new Cookie("loginPwd","0");
                    loginPwd.setMaxAge(0);
                    response.addCookie(loginPwd);
                }
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
        petOwner.setPid("0-"+UUIDUtils.getUUID());
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

    @RequestMapping("/settings/qx/user/toDetail")
    public String toDetail(){
        return "settings/qx/user/detail";
    }

    @RequestMapping("/settings/qx/user/loginOut")
    public String loginOut(HttpSession session,HttpServletResponse response){
        //销毁session中的所有信息
        session.invalidate();
        //销毁Cookie
        Cookie loginAct=new Cookie("loginAct","0");
        loginAct.setMaxAge(0);
        response.addCookie(loginAct);

        Cookie loginPwd=new Cookie("loginPwd","0");
        loginPwd.setMaxAge(0);
        response.addCookie(loginPwd);

        return "redirect:settings/qx/user/toLogin";
    }
}
