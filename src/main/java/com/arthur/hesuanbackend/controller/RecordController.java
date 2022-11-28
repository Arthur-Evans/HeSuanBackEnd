package com.arthur.hesuanbackend.controller;

import com.arthur.hesuanbackend.common.BaseResponse;
import com.arthur.hesuanbackend.common.ErrorCode;
import com.arthur.hesuanbackend.constant.StaticData;
import com.arthur.hesuanbackend.exception.BusinessException;
import com.arthur.hesuanbackend.model.domain.Citizen;
import com.arthur.hesuanbackend.model.domain.Record;
import com.arthur.hesuanbackend.model.request.RecordRequest;
import com.arthur.hesuanbackend.model.response.RecordResponse;
import com.arthur.hesuanbackend.service.CitizenService;
import com.arthur.hesuanbackend.service.RecordService;
import com.arthur.hesuanbackend.utils.ResultUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Resource
    private RecordService recordService;

    @Resource
    private CitizenService citizenService;


    @PostMapping("/add")
    public BaseResponse<RecordResponse> RecordADD(@RequestBody RecordRequest request){
        if (StringUtils.isAnyBlank(request.getStatus())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"健康信息不能为空");
        }
        if(request.getId()<=0)
           throw new BusinessException(ErrorCode.PARAMS_ERROR,"id不能小于等于0");
        if(request.getTemperature()<20||request.getTemperature()>100)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"温度在[20，100]之间");
        Citizen citizen = citizenService.getById(request.getId());
        if(citizen==null)
            throw new BusinessException(ErrorCode.RESULT_NULL_ERROR,"输入的微信账号错误");

        Record record =new Record();
        record.setCitizenID(request.getId());
        record.setHsDiagnosis(request.getDiagnosis());
        record.setHsHigh(request.getHigh());
        record.setHsTemper(request.getTemperature());
        record.setHsStatus(request.getStatus());
        RecordResponse recordResponse =new RecordResponse();
        if(record.getHsTemper()>38 || record.getHsDiagnosis()==1)
            recordResponse.setResult(StaticData.RESULT_RED);
        else if(record.getHsHigh()==1)
            recordResponse.setResult(StaticData.RESULT_YELLOW);
        else
            recordResponse.setResult(StaticData.RESULT_GREEN);
        boolean saveResult = recordService.save(record);
        if(!saveResult){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据库插入失败");
        }
        return ResultUtils.success(recordResponse,"打卡成功");
    }


    @GetMapping("/ownList")
    public BaseResponse<List<Record>> getOwnList(int citizenId){
        if(citizenId<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"id不能小于等于0");
        }
        QueryWrapper<Record> listQueryWrapper =new QueryWrapper<>();
        listQueryWrapper.eq("citizenID",citizenId);
        List<Record> recordList = recordService.list(listQueryWrapper);
        if(recordList==null)
            throw new BusinessException(ErrorCode.RESULT_NULL_ERROR);
        recordList.sort((t1, t2) -> t2.getHsTime().compareTo(t1.getHsTime()));
        return ResultUtils.success(recordList,"返回所有最新的记录");
    }

    @GetMapping("/ownNew")
    public BaseResponse<Record> getOwnNEW(int citizenId){
        if(citizenId<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"id不能小于等于0");
        }
        QueryWrapper<Record> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("citizenID",citizenId).orderByDesc("recordId").last("limit 1");
        Record result =recordService.getOne(queryWrapper);
        if(result==null)
            throw new BusinessException(ErrorCode.RESULT_NULL_ERROR);
       return ResultUtils.success(result,"返回最新一条成功");
    }

}
