package com.guojijian.pethospital.settings.dto;

import com.guojijian.pethospital.workbench.pojo.Foster;

public class FosterDto extends Foster {
    //便于页面显示的属性
    private String name;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
