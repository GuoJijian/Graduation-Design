package com.guojijian.pethospital.workbench.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/workbench/toIndex")
    public String toIndex(){
        return "workbench/index";
    }
}
