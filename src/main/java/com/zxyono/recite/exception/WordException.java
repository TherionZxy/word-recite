package com.zxyono.recite.exception;

import com.zxyono.recite.enums.ExceptionEnum;

public class WordException extends BaseException {
    public WordException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }
}