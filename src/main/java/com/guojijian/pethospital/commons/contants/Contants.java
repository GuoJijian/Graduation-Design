package com.guojijian.pethospital.commons.contants;
//常用常量类
public class Contants {
    //保存ReturnObject类中的code值
    public static final String RETURN_OBJECT_CODE_SUCCESS="1";
    public static final String RETURN_OBJECT_CODE_FAIL="0";
    //保存Session中的用户
    public static final String SESSION_USER="user";
    //保存用户权限等级
    public static final String GRADE_LEVEL_ONE="1";
    public static final String GRADE_LEVEL_TWO="2";
    public static final String GRADE_LEVEL_THREE="3";
    public static final String GRADE_LEVEL_FOUR="4";
    //保存预约状态（0：已取消；1：已预约；2：已完成）
    public static final String APPOINTMENT_STATUS_CANCEL="0";
    public static final String APPOINTMENT_STATUS_ADVANCE="1";
    public static final String APPOINTMENT_STATUS_PERFORM="2";
    //保存门诊类型对应的费用
    public static final Float ODTYPE_HEALTH=9f;
    public static final Float ODTYPE_COMMON=18f;
    public static final Float ODTYPE_EXPERT=36f;
}
