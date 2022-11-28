package com.arthur.hesuanbackend.service.impl;

import com.arthur.hesuanbackend.common.ErrorCode;
import com.arthur.hesuanbackend.exception.BusinessException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.arthur.hesuanbackend.model.domain.Access;
import com.arthur.hesuanbackend.service.AccessService;
import com.arthur.hesuanbackend.mapper.AccessMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author ArthurEvans
* @description 针对表【Access】的数据库操作Service实现
* @createDate 2022-11-20 15:43:53
*/
@Slf4j
@Service
public class AccessServiceImpl extends ServiceImpl<AccessMapper, Access>
    implements AccessService{

    @Resource
    private AccessMapper accessMapper;

    @Override
    public void applyThrough(Access access) {
        boolean saveResult = this.save(access);
        if(!saveResult){
            log.info(access.getUserId()+"申请失败");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据库插入失败");
        }
        log.info(access.getUserId()+"申请成功");
    }


}





