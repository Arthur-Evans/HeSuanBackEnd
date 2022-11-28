package com.arthur.hesuanbackend.controller;

import com.arthur.hesuanbackend.common.BaseResponse;
import com.arthur.hesuanbackend.common.ErrorCode;
import com.arthur.hesuanbackend.exception.BusinessException;
import com.arthur.hesuanbackend.model.domain.Access;
import com.arthur.hesuanbackend.service.AccessService;
import com.arthur.hesuanbackend.utils.ResultUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/access")
public class AccessController {

    @Resource
    private AccessService accessService;


    @PostMapping("/apply")
    public BaseResponse<String> access(@RequestParam String userID,@RequestParam String reason,@RequestParam String leaveAddress,
                               @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")Date leaveTime,
                               @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")Date backTime,
                               MultipartFile photo) throws IOException {
        if(StringUtils.isAnyBlank(userID,reason,leaveAddress))
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        if(leaveTime==null||backTime==null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"日期不能为空");
        Access access =new Access();
        access.setUserId(userID);
        access.setReason(reason);
        access.setLeaveAddress(leaveAddress);
        access.setLeaveTime(leaveTime);
        access.setBackTime(backTime);
        if(photo!=null){
            byte[] file = photo.getBytes();
            access.setPhoto(file);
        }
        accessService.applyThrough(access);
        return ResultUtils.success("申请成功");
    }


    @PostMapping("/statusById")
    public BaseResponse<List<Access>> getAuditListAccess(@RequestParam  String userID,@RequestParam Integer statusID){
        if(StringUtils.isAnyBlank(userID) || statusID <=0)
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        QueryWrapper<Access> accessQuery =new QueryWrapper<>();
        accessQuery.eq("userId",userID);
        accessQuery.eq("status",statusID);
        List<Access> result = accessService.list(accessQuery);
        if(result==null){
            throw new BusinessException(ErrorCode.RESULT_NULL_ERROR);
        }
        return ResultUtils.success(result,"得到所有指定状态的出入申请数据");
    }


    @GetMapping("/listById")
    public BaseResponse<List<Access>> getListAccess(String userID){
        if(StringUtils.isAnyBlank(userID))
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        QueryWrapper<Access> accessQuery =new QueryWrapper<>();
        accessQuery.eq("userId",userID);
        List<Access> result = accessService.list(accessQuery);
        if(result==null){
            throw new BusinessException(ErrorCode.RESULT_NULL_ERROR);
        }
        return ResultUtils.success(result,"得到所有的申请数据");
    }

    @GetMapping("/newById")
    public BaseResponse<Access> getNewAccess(String userID){
        if(StringUtils.isAnyBlank(userID))
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        QueryWrapper<Access> accessQuery =new QueryWrapper<>();
        accessQuery.eq("userId",userID).orderByDesc("id").last("limit 1");
        Access result = accessService.getOne(accessQuery);
        if(result==null){
            throw new BusinessException(ErrorCode.RESULT_NULL_ERROR);
        }
        return ResultUtils.success(result,"得到最新的申请数据");
    }

}
