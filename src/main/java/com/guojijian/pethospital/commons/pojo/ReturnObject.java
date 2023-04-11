package com.guojijian.pethospital.commons.pojo;

import com.guojijian.pethospital.commons.contants.Contants;

//公共返回类，用于存储异步请求的响应数据
public class ReturnObject {
    private String code;
    private String message;
    private Object retObject;

    public ReturnObject() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetObject() {
        return retObject;
    }

    public void setRetObject(Object retObject) {
        this.retObject = retObject;
    }

    public static ReturnObject success(Object object){
        ReturnObject ro=new ReturnObject();
        ro.code=Contants.RETURN_OBJECT_CODE_SUCCESS;
        ro.retObject=object;
        return ro;
    }
}
