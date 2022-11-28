package com.arthur.hesuanbackend.service;

import com.arthur.hesuanbackend.model.domain.Access;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ArthurEvans
* @description 针对表【Access】的数据库操作Service
* @createDate 2022-11-20 15:43:53
*/
public interface AccessService extends IService<Access> {



    void applyThrough(Access access);


}
