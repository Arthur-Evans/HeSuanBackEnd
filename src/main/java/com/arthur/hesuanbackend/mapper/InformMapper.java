package com.arthur.hesuanbackend.mapper;


import com.arthur.hesuanbackend.model.domain.Inform;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ArthurEvans
* @description 针对表【Inform】的数据库操作Mapper
* @createDate 2022-11-20 22:39:24
* @Entity generator.domain.Inform
*/
public interface InformMapper extends BaseMapper<Inform> {

    /**
     * 获取公告信息
     */
    List<Inform> getInformList(@Param("userId") String userId);

}




