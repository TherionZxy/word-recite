package com.zxyono.recite.exception;

import com.zxyono.recite.enums.ExceptionEnum;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public BaseException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
    }
}