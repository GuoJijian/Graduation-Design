package com.guojijian.pethospital.settings.service.impl;

import com.guojijian.pethospital.settings.mapper.PetOwnerMapper;
import com.guojijian.pethospital.settings.pojo.PetOwner;
import com.guojijian.pethospital.settings.service.PetOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("petOwnerService")
public class PetOwnerServiceImpl implements PetOwnerService {

    @Autowired
    private PetOwnerMapper petOwnerMapper;

    @Override
    public int createPetOwner(PetOwner petOwner) {
        return petOwnerMapper.insertPetOwner(petOwner);
    }

    @Override
    public PetOwner queryPetOwnerById(String id) {
        return petOwnerMapper.selectByPrimaryKey(id);
    }
}
