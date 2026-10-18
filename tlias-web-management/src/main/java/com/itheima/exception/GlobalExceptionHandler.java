package com.itheima.exception;

import com.itheima.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//全局异常处理器
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleException(Exception e){
        log.error("程序出错了",e);
        return Result.error("出错了，请联系工作人员");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result handlerDuplicateKeyException(DuplicateKeyException e){
        log.error("程序出错了",e);
        String message = e.getMessage();
        int i = message.indexOf("Duplicate entry");
        if (i < 0) {
            return Result.error("\u6570\u636e\u91cd\u590d\uff0c\u5df2\u7ecf\u5b58\u5728");
        }
        String errMsg = message.substring(i);
        String[] arr = errMsg.split(" ");
        String value = arr.length > 2 ? arr[2].replace("'", "") : errMsg;
        return Result.error(value + "\u5df2\u7ecf\u5b58\u5728");

    }
}
