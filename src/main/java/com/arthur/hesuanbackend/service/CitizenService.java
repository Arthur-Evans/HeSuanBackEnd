package com.arthur.hesuanbackend.service;

import com.arthur.hesuanbackend.model.domain.Citizen;
import com.arthur.hesuanbackend.model.request.CitizenRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ArthurEvans
* @description 针对表【Citizen】的数据库操作Service
* @createDate 2022-11-14 15:27:19
*/
public interface CitizenService extends IService<Citizen> {

    Citizen findByWXID(String id);

    void Bind(CitizenRequest citizenRequest);

    void update(CitizenRequest citizenRequest);
}
