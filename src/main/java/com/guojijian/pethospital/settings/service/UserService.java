package com.guojijian.pethospital.settings.service;

import com.guojijian.pethospital.settings.pojo.Employee;
import com.guojijian.pethospital.settings.pojo.PetOwner;

public interface UserService {
    /**
     * 通过账号和密码查询登录信息
     */
    Object queryLoginInfoByActAndPwd(PetOwner petOwner);
}
