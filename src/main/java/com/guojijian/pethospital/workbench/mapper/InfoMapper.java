package com.guojijian.pethospital.workbench.mapper;

import com.guojijian.pethospital.workbench.pojo.Info;
import java.util.List;
import java.util.Map;

public interface InfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_information
     *
     * @mbggenerated Thu Feb 09 17:07:02 CST 2023
     */
    int deleteByPrimaryKey(String iId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_information
     *
     * @mbggenerated Thu Feb 09 17:07:02 CST 2023
     */
    int insert(Info record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_information
     *
     * @mbggenerated Thu Feb 09 17:07:02 CST 2023
     */
    Info selectByPrimaryKey(String iId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_information
     *
     * @mbggenerated Thu Feb 09 17:07:02 CST 2023
     */
    List<Info> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_information
     *
     * @mbggenerated Thu Feb 09 17:07:02 CST 2023
     */
    int updateByPrimaryKey(Info record);

    /**
     * 创建信息
     */
    int insertInfo(Info info);

    /**
     * 根据条件模糊查询信息
     */
    List<Info> selectInfoForPageByCondition(Map<String,Object> map);

    /**
     * 根据id查询信息
     */
    Info selectInfoById(String id);

    /**
     * 修改信息
     */
    int updateInfo(Info info);

    /**
     * 根据id批量删除信息
     * @param ids
     * @return
     */
    int deleteInfoByIds(String[] ids);
}