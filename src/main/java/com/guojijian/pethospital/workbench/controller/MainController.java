package com.guojijian.pethospital.workbench.controller;

import com.guojijian.pethospital.commons.contants.Contants;
import com.guojijian.pethospital.settings.pojo.Employee;
import com.guojijian.pethospital.settings.pojo.PetOwner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    @RequestMapping("/workbench/toIndex")
    public String toIndex(String index, Model model){
        model.addAttribute("index",index);
        return "/workbench/index";
    }
}
