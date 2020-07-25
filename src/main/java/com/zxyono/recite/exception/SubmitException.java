package com.zxyono.recite.exception;

import com.zxyono.recite.enums.ExceptionEnum;

public class SubmitException extends BaseException {
    public SubmitException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }
}