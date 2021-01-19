package com.zxyono.recite.exception;

import com.zxyono.recite.enums.ExceptionEnum;

public class BaiduApiException extends BaseException {
    public BaiduApiException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }
}