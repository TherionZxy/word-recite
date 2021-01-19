package com.zxyono.recite.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    WORD_PARAM_EXCEPTION(40001, "word exception", "word类参数传递错误"),
    SUBMIT_PARAM_EXCEPTION(40002, "submit exception", "submit参数传递错误"),

    BAIDU_AUDIO_API_EXCEPTION(50001, "baidu audio api exception", "百度语音API调用异常");

    private Integer code;
    private String type;
    private String message;

    public ExceptionEnum addMessage(String message) {
        this.message = message;
        return this;
    }

    public ExceptionEnum addMessage(String message, Object...args) {
        this.message = String.format(message, args);
        return this;
    }

    public ExceptionEnum addMoreMessage(String message) {
        this.message = this.message + message;
        return this;
    }
}