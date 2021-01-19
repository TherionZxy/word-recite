package com.zxyono.recite.exception.handler;

import com.zxyono.recite.exception.BaiduApiException;
import com.zxyono.recite.exception.SubmitException;
import com.zxyono.recite.exception.WordException;
import com.zxyono.recite.utils.ResultMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = WordException.class)
    public ResultMap handlerWordException(WordException e) {
        return ResultMap.error(e.getExceptionEnum().getCode(), e.getExceptionEnum().getMessage());
    }

    @ExceptionHandler(value = SubmitException.class)
    public ResultMap handlerSubmitException(SubmitException e) {
        return ResultMap.error(e.getExceptionEnum().getCode(), e.getExceptionEnum().getMessage());
    }

    @ExceptionHandler(value = BaiduApiException.class)
    public ResultMap handlerBaiduApiException(BaiduApiException e) {
        return ResultMap.error(e.getExceptionEnum().getCode(), e.getExceptionEnum().getMessage());
    }
}