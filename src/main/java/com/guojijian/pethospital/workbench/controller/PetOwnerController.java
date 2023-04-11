package com.guojijian.pethospital.workbench.controller;

import com.guojijian.pethospital.settings.pojo.PetOwner;
import com.guojijian.pethospital.settings.service.PetOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/workbench/petOwner")
public class PetOwnerController {

    @Autowired
    private PetOwnerService petOwnerService;

    @GetMapping
    public Object queryPetOwnerByPhone(String phone){
        List<PetOwner> petOwnerList=petOwnerService.queryPetOwnerForFuzzyByPhone(phone);
        return petOwnerList;
    }
}
