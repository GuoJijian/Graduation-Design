package com.guojijian.pethospital.workbench.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppointmentController {
    @RequestMapping("/workbench/appointment/toIndex")
    public String toIndex(){
        return "workbench/appointment/index";
    }
}
