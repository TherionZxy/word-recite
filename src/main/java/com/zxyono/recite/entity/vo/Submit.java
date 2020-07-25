package com.zxyono.recite.entity.vo;

import lombok.Data;

import java.util.Arrays;

@Data
public class Submit {
    private Long[] wordIds;
    private String[] answers;

    // 检查提交的答案是否符合规范
    public boolean checkSubmit() {
        // 1. wordIds 和 answers 任何一方都不能为空
        if (wordIds == null || answers == null) {
            return false;
        }

        // 2. wordIds 和 answers的长度必须一致
        if (wordIds.length != answers.length) {
            return false;
        }

        // 3. 原则上同一题（同一单词不可能出现重复）
        if (Arrays.stream(wordIds).distinct().toArray().length < wordIds.length) {
            return false;
        }

        return true;
    }
}