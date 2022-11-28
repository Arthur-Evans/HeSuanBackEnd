package com.arthur.hesuanbackend.controller;

import com.arthur.hesuanbackend.common.BaseResponse;
import com.arthur.hesuanbackend.common.ErrorCode;
import com.arthur.hesuanbackend.exception.BusinessException;
import com.arthur.hesuanbackend.mapper.InformMapper;
import com.arthur.hesuanbackend.model.domain.Inform;
import com.arthur.hesuanbackend.model.domain.Notice;
import com.arthur.hesuanbackend.service.NoticeService;
import com.arthur.hesuanbackend.utils.ResultUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @Resource
    private InformMapper informMapper;


    @GetMapping("/findAll")
    public BaseResponse<List<Notice>> findAll(String userId){
        if(StringUtils.isAnyBlank(userId)){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        QueryWrapper<Notice> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("status",1);
        queryWrapper.eq("allPerson",1);
        List<Notice> noticeList1 = noticeService.list(queryWrapper);
        List<Inform> informList = informMapper.getInformList(userId);
        List<Notice> noticeList2 = new ArrayList<>();
        for (Inform inform:informList) {
            noticeList2.add(inform.getNotice());
        }
        List<Notice> allNotice = Stream.of(noticeList1, noticeList2).flatMap(Collection::stream).collect(Collectors.toList());
        return ResultUtils.success(allNotice,"返回所有公告");
    }

    @GetMapping("/findUniverse")
    public BaseResponse<List<Notice>> findUniverse(String userId){
        if(StringUtils.isAnyBlank(userId)){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        QueryWrapper<Notice> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("status",1);
        queryWrapper.eq("allPerson",1);
        List<Notice> noticeList = noticeService.list(queryWrapper);
        return ResultUtils.success(noticeList,"返回公开公告");
    }

    @GetMapping("/findPrivate")
    public BaseResponse<List<Notice>> find(String userId){
        if(StringUtils.isAnyBlank(userId)){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        List<Inform> informList = informMapper.getInformList("1");
        List<Notice> noticeList =new ArrayList<>();
        for (Inform inform:informList) {
            noticeList.add(inform.getNotice());
        }
        return ResultUtils.success(noticeList,"返回单独公告");
    }







}
