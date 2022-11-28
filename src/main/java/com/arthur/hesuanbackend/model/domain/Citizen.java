package com.arthur.hesuanbackend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName Citizen
 */
@TableName(value ="Citizen")
@Data
public class Citizen implements Serializable {
    /**
     * 居民ID
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 居民姓名
     */
    private String name;

    /**
     * 居民年龄
     */
    private Integer age;

    /**
     * 居民性别
     */
    private String gender;

    /**
     * 身份证号
     */
    private String card;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地区
     */
    private String region;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 账号ID
     */
    private String userID;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}