package com.arthur.hesuanbackend.common;

/**
 * 全局错误码
 *
 */

public enum ErrorCode {

    SUCCESS(200,"OK",""),

    RESULT_NULL_ERROR(201,"返回结果为空",""),
    PARAMS_ERROR(301,"请求参数错误",""),

    PARAMS_NULL_ERROR(302,"请求参数为空",""),

    NOT_LOGIN(401,"未登录",""),

    NO_AUTH(402,"没有权限访问",""),

    SYSTEM_ERROR(500,"系统内部异常","");

    /**
     * 状态码信息
     */
    private int code;


    /**
     * 状态码详细
     */
    private  final String message;

    private  final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
