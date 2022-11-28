package com.arthur.hesuanbackend.controller;

import com.arthur.hesuanbackend.common.BaseResponse;
import com.arthur.hesuanbackend.common.ErrorCode;
import com.arthur.hesuanbackend.exception.BusinessException;
import com.arthur.hesuanbackend.model.domain.Citizen;
import com.arthur.hesuanbackend.model.request.CitizenRequest;
import com.arthur.hesuanbackend.service.CitizenService;
import com.arthur.hesuanbackend.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/citizen")
public class CitizenController {



    @Resource
    private CitizenService citizenService;

    // 根据主键ID查找居民信息
    @GetMapping("/search")
    public BaseResponse<Citizen> searchUser(Integer id){
        if(id<0){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        Citizen citizen = citizenService.getById(id);
        if(citizen==null)
            throw new BusinessException(ErrorCode.RESULT_NULL_ERROR,"未记录微信ID");
        return ResultUtils.success(citizen,"查询成功");
    }


    @GetMapping("/find")
    public BaseResponse<Citizen> findCitizen(String WXID){
        if(StringUtils.isAnyBlank(WXID)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(WXID.length()>255){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Citizen citizen =citizenService.findByWXID(WXID);
        if(citizen==null) throw new BusinessException(ErrorCode.RESULT_NULL_ERROR,"未记录微信ID");
        return ResultUtils.success(citizen,"查询成功");
    }


    @PostMapping("/bind")
    public BaseResponse<String> bindCitizen(@RequestBody CitizenRequest request){
        VerifyCitizen(request);
        citizenService.Bind(request);
        return ResultUtils.success("绑定成功");
    }

    @PostMapping("/update")
    public BaseResponse<String> updateCitizen(@RequestBody CitizenRequest request){
        VerifyCitizen(request);
        citizenService.update(request);
        return ResultUtils.success("更新成功");
    }

    private void VerifyCitizen(@RequestBody CitizenRequest request) {
        if(StringUtils.isAnyBlank(request.getId(),request.getGender(),
                request.getCard(),request.getName(),request.getPhone()
                ,request.getRegion(),request.getAddress())){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"参数不能为空");
        }
        if(request.getAge()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"输入年龄不合法");
        }
    }

}
