package com.guojijian.pethospital.settings.mapper;

import com.guojijian.pethospital.settings.pojo.DicValue;

import java.util.List;

public interface DicValueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Fri Feb 10 10:37:49 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Fri Feb 10 10:37:49 CST 2023
     */
    int insert(DicValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Fri Feb 10 10:37:49 CST 2023
     */
    DicValue selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Fri Feb 10 10:37:49 CST 2023
     */
    List<DicValue> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Fri Feb 10 10:37:49 CST 2023
     */
    int updateByPrimaryKey(DicValue record);

    /**
     * 根据typeCode查询DicValue
     */
    List<DicValue> selectDicValueByTypeCode(String typeCode);

    /**
     * 根据id查询Value
     */
    String selectValueById(String id);
}