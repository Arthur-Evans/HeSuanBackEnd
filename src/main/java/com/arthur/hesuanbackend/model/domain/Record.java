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
 * @TableName Record
 */
@TableName(value ="Record")
@Data
public class Record implements Serializable {
    /**
     * 打卡记录ID
     */
    @TableId(type = IdType.AUTO)
    private Object recordId;

    /**
     * 打卡时间
     */
    private Date hsTime;

    /**
     * 打卡体温
     */
    private Double hsTemper;

    /**
     * 打卡状态
     */
    private String hsStatus;

    /**
     * 是否7天内从中高风险地区归来 1是0否
     */
    private Integer hsHigh;

    /**
     * 是否接触过确诊或疑似病例   1是0否
     */
    private Integer hsDiagnosis;

    /**
     * 居民绑定ID 外键
     */
    private Object citizenID;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}