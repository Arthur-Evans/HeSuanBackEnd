package com.arthur.hesuanbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.arthur.hesuanbackend.model.domain.Notice;
import com.arthur.hesuanbackend.service.NoticeService;
import com.arthur.hesuanbackend.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

/**
* @author ArthurEvans
* @description 针对表【Notice】的数据库操作Service实现
* @createDate 2022-11-14 19:51:37
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
    implements NoticeService{

}




