package com.guojijian.pethospital.settings.service;

import com.guojijian.pethospital.settings.pojo.PetOwner;

public interface PetOwnerService {
    //添加用户
    int createPetOwner(PetOwner petOwner);

    //通过id查询用户
    PetOwner queryPetOwnerById(String id);
}
