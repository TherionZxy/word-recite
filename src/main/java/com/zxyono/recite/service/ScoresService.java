package com.zxyono.recite.service;

import com.zxyono.recite.entity.Scores;
import com.zxyono.recite.entity.vo.Submit;
import com.zxyono.recite.entity.wrapper.ScoresWrapper;

import java.util.List;

public interface ScoresService {
    public Scores findScoresById(Long scoresId);

    public Scores addScores(Submit submit);

    public ScoresWrapper findAll(int page, int size);
}
