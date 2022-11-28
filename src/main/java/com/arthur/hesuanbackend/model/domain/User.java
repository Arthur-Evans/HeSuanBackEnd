package com.arthur.hesuanbackend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName User
 */
@TableName(value ="User")
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户角色
     */
    private Integer userRole;

    /**
     * 用户创建时间
     */
    private Date userCreateTime;

    /**
     * 用户状态 默认 1 正常    0 删除
     */
    private Integer userStatus;

    /**
     * 
     */
    private String userRealName;

    /**
     * 
     */
    private byte[] userProfile;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}