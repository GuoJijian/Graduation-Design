package com.guojijian.pethospital.workbench.service;

import com.guojijian.pethospital.workbench.pojo.InfoComment;

import java.util.List;

public interface InfoCommentService {
    /**
     * 创建信息评论
     */
    int createInfoComment(InfoComment infoComment);

    /**
     * 根据信息id查询信息评论
     */
    List<InfoComment> queryInfoCommentByInfoId(String infoId);

    /**
     * 根据id删除评论
     */
    int dropInfoCommentById(String id);
}
