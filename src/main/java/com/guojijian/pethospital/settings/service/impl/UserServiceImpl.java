package com.guojijian.pethospital.settings.service.impl;

import com.guojijian.pethospital.settings.mapper.EmployeeMapper;
import com.guojijian.pethospital.settings.mapper.PetOwnerMapper;
import com.guojijian.pethospital.settings.pojo.Employee;
import com.guojijian.pethospital.settings.pojo.PetOwner;
import com.guojijian.pethospital.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private PetOwnerMapper petOwnerMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Object queryLoginInfoByActAndPwd(PetOwner petOwner) {
        PetOwner po=petOwnerMapper.selectPetOwnerByActAndPwd(petOwner);
        if(po!=null){
            return po;
        }
        Employee ep0=new Employee();
        ep0.seteUserName(petOwner.getpUserName());
        ep0.setePassword(petOwner.getpPassword());
        Employee ep=employeeMapper.selectEmployeeByActAndPwd(ep0);
        if(ep!=null){
            return ep;
        }

        return null;
    }
}
