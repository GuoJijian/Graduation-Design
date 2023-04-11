package com.guojijian.pethospital.workbench.controller;

import com.guojijian.pethospital.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workbench/dicValue")
public class DicValueController {

    @Autowired
    private DicValueService dicValueService;

    @GetMapping
    public Object queryDicValue(String typeCode){
        return dicValueService.queryDicValueByTypeCode(typeCode);
    }
}
