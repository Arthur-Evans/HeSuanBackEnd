package com.arthur.hesuanbackend.exception;



import com.arthur.hesuanbackend.common.BaseResponse;
import com.arthur.hesuanbackend.common.ErrorCode;
import com.arthur.hesuanbackend.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 切面功能
 * Spring AOP
 * 全局捕获异常
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse BusinessExceptionHandler(BusinessException e){
        log.error("businessException"+e.getMessage(),e);
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }

    @ExceptionHandler()
    public BaseResponse runtimeExceptionHandler(RuntimeException e){
        log.error("RuntimeException",e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }

}
