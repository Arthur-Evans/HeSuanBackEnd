package com.arthur.hesuanbackend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName Inform
 */
@Data
public class Inform implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 
     */
    private Integer noticeId;
    /**
     * 
     */
    private Integer userId;

    private Notice notice;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}