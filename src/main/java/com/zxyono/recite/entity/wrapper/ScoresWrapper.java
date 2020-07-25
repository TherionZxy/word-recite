package com.zxyono.recite.entity.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zxyono.recite.entity.Scores;
import lombok.Data;

import java.util.List;

@Data
public class ScoresWrapper {
    private List<Scores> scoresList;
    private int page;
    private int total;
}