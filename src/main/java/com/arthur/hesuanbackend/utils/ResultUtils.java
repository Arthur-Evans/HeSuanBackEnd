package com.arthur.hesuanbackend.utils;


import com.arthur.hesuanbackend.common.BaseResponse;
import com.arthur.hesuanbackend.common.ErrorCode;

/**
 * 返回工具类
 * @author Arthur
 */

public class ResultUtils {

    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */

    public static <T > BaseResponse<T> success(T data){
        return new BaseResponse<>(200,data,"ok");
    }
    public static <T > BaseResponse<T> success(T data,String ds){
        return new BaseResponse<>(200,data,"ok",ds);
    }
    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }



    public static BaseResponse error(ErrorCode errorCode,String message,String description){

        return new BaseResponse(errorCode.getCode(),null,message,description);
    }


    public static BaseResponse error(ErrorCode errorCode,String description){

        return new BaseResponse(errorCode.getCode(), errorCode.getMessage(),description);
    }


    /**
     * 失败
     * @param Code
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(int Code,String message,String description){

        return new BaseResponse(Code,null,message,description);
    }


}
