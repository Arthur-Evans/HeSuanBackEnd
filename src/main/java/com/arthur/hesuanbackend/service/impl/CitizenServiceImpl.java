package com.arthur.hesuanbackend.service.impl;

import com.arthur.hesuanbackend.common.ErrorCode;
import com.arthur.hesuanbackend.exception.BusinessException;
import com.arthur.hesuanbackend.model.request.CitizenRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.arthur.hesuanbackend.model.domain.Citizen;
import com.arthur.hesuanbackend.service.CitizenService;
import com.arthur.hesuanbackend.mapper.CitizenMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author ArthurEvans
* @description 针对表【Citizen】的数据库操作Service实现
* @createDate 2022-11-14 15:27:19
*/
@Slf4j
@Service
public class CitizenServiceImpl extends ServiceImpl<CitizenMapper, Citizen>
    implements CitizenService{


    @Resource
    private CitizenMapper citizenMapper;

    @Override
    public Citizen findByWXID(String id) {
        QueryWrapper<Citizen> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("userID",id);
        return citizenMapper.selectOne(queryWrapper);
    }

    @Override
    public void Bind(CitizenRequest citizenRequest) {
        Citizen citizen = Verify(citizenRequest);
        boolean saveResult = this.save(citizen);
        if(!saveResult){
            log.info(citizen+"绑定失败");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据库插入失败");
        }
        log.info(citizen+"绑定成功");
    }

    @Override
    public void update(CitizenRequest citizenRequest) {
        Citizen citizen = Verify(citizenRequest);
        UpdateWrapper<Citizen> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("userID",citizen.getUserID());
        int result = citizenMapper.update(citizen, updateWrapper);
        if(result<=0){
            throw  new BusinessException(ErrorCode.SYSTEM_ERROR,"更新失败");
        }
    }


    public Citizen Verify(CitizenRequest citizenRequest){
        Citizen citizen =new Citizen();
        citizen.setName(citizenRequest.getName());
        citizen.setAge(citizenRequest.getAge());
        citizen.setGender(citizenRequest.getGender());
        citizen.setCard(citizenRequest.getCard());
        citizen.setPhone(citizenRequest.getPhone());
        citizen.setRegion(citizenRequest.getRegion());
        citizen.setAddress(citizenRequest.getAddress());
        citizen.setUserID(citizenRequest.getId());
        // 校验信息
        //性别
        String gender = citizen.getGender();
        if(!gender.equals("男")&&!gender.equals("女")){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"性别不男不女");
        }
        if(citizen.getCard().length()!=18){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"身份证号错误");
        }
        // 开头的"1"代表第一位为数字1，"[3-9]"代表第二位可以为3、4、5、6、7、8、9其中之一，"\\d{9}"代表后面是9位数字
        String regex = "1[3-9]\\d{9}";
        if(!citizen.getPhone().matches(regex)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"手机号错误");
        }
        return citizen;
    }


}




