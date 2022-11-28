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
 * @TableName Access
 */
@TableName(value ="Access")
@Data
public class Access implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 微信ID
     */
    private String userId;

    /**
     * 1 申请中 2 通过 3 拒绝
     */
    private Integer status;

    /**
     * 申请理由
     */
    private String reason;

    /**
     * 最后离开地址
     */
    private String leaveAddress;

    /**
     * 离开时间
     */
    private Date leaveTime;

    /**
     * 返回时间
     */
    private Date backTime;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 申请的管理员
     */
    private String manageId;

    /**
     * 申请图片
     */
    private byte[] photo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}