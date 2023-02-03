package com.guojijian.pethospital.settings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @RequestMapping("/settings/qx/user/toLogin")
    public String toLogin(){
        return "/settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login")
    @ResponseBody
    public Object login(){
        return "";
    }
}
