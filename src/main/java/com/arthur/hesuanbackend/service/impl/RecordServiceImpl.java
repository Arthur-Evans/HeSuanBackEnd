package com.arthur.hesuanbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.arthur.hesuanbackend.model.domain.Record;
import com.arthur.hesuanbackend.service.RecordService;
import com.arthur.hesuanbackend.mapper.RecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author ArthurEvans
* @description 针对表【Record】的数据库操作Service实现
* @createDate 2022-11-14 17:16:03
*/
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record>
    implements RecordService{

    @Resource
    private RecordMapper mapper;

    
}




