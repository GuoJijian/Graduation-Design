package com.guojijian.pethospital.settings.service;

import com.guojijian.pethospital.settings.pojo.PetOwner;

import java.util.List;

public interface PetOwnerService {
    //添加用户
    int createPetOwner(PetOwner petOwner);

    //通过id查询用户
    PetOwner queryPetOwnerById(String id);

    /**
     * 根据电话号码模糊查询用户
     */
    List<PetOwner> queryPetOwnerForFuzzyByPhone(String phone);
}
