package com.zxyono.recite.entity.vo;

import com.zxyono.recite.enums.ExceptionEnum;
import com.zxyono.recite.exception.WordException;
import lombok.Data;

import java.util.Arrays;

@Data
public class WordVo {
    private String[] en;
    private String[] zh;

    public boolean checkWordVo() {
        if (en == null || zh == null) {
            return false;
        }

        if (en.length != zh.length) {
            return false;
        }

        if (Arrays.stream(en).distinct().toArray().length < en.length) {
            return false;
        }

        return true;
    }
}