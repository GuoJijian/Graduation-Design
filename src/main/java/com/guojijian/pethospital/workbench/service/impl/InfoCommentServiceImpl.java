package com.guojijian.pethospital.workbench.service.impl;

import com.guojijian.pethospital.workbench.mapper.InfoCommentMapper;
import com.guojijian.pethospital.workbench.pojo.InfoComment;
import com.guojijian.pethospital.workbench.service.InfoCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("infoCommentService")
public class InfoCommentServiceImpl implements InfoCommentService {

    @Autowired
    private InfoCommentMapper infoCommentMapper;

    @Override
    public int createInfoComment(InfoComment infoComment) {
        return infoCommentMapper.insertInfoComment(infoComment);
    }

    @Override
    public List<InfoComment> queryInfoCommentByInfoId(String infoId) {
        return infoCommentMapper.selectInfoCommentByInfoId(infoId);
    }

    @Override
    public int dropInfoCommentById(String id) {
        return infoCommentMapper.deleteInfoCommentById(id);
    }
}
