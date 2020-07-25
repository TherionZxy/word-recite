package com.zxyono.recite.service.impl;

import com.zxyono.recite.entity.Answer;
import com.zxyono.recite.entity.Scores;
import com.zxyono.recite.entity.Word;
import com.zxyono.recite.entity.vo.Submit;
import com.zxyono.recite.entity.wrapper.ScoresWrapper;
import com.zxyono.recite.enums.ExceptionEnum;
import com.zxyono.recite.exception.SubmitException;
import com.zxyono.recite.repository.ScoresRepository;
import com.zxyono.recite.service.ScoresService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScoresServiceImpl implements ScoresService {
    @Resource
    private ScoresRepository scoresRepository;

    @Override
    public Scores findScoresById(Long scoresId) {
        return scoresRepository.getOne(scoresId);
    }

    @Override
    public ScoresWrapper findAll(int page, int size) {
        Page<Scores> all = scoresRepository.findAll(PageRequest.of(page, size));
        ScoresWrapper scoresWrapper = new ScoresWrapper();
        scoresWrapper.setScoresList(all.getContent());
        scoresWrapper.setTotal(all.getTotalPages());
        scoresWrapper.setPage(page);
        return scoresWrapper;
    }

    @Override
    public boolean addScores(Submit submit) {
        // 拿到submit之后，组装Scores，进行保存
        Scores scores = new Scores();
        List<Answer> answersList = new ArrayList<>();

        if (!submit.checkSubmit()) {
            throw new SubmitException(ExceptionEnum.SUBMIT_PARAM_EXCEPTION);
        }

        Long[] wordIds = submit.getWordIds();
        String[] answers = submit.getAnswers();
        for (int i = 0; i<wordIds.length; i++) {
            Answer answer = new Answer();
            Word word = new Word();
            word.setWordId(wordIds[i]);
            answer.setWord(word);
            answer.setEnAnswer(answers[i]);

            answersList.add(answer);
        }

        scores.setAnswers(answersList);
        Scores result = scoresRepository.save(scores);
        return true;
    }
}