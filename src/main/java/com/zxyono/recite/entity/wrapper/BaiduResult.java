package com.zxyono.recite.entity.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BaiduResult {
    private String from;
    private String to;
    @JsonProperty(value = "trans_result")
    private List<Map<String, String>> transResult;
}