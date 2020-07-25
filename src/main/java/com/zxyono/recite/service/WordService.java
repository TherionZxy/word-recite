package com.zxyono.recite.service;

import com.zxyono.recite.entity.Word;
import com.zxyono.recite.entity.vo.WordVo;
import com.zxyono.recite.entity.wrapper.WordWrapper;

import java.util.List;

public interface WordService {
    public List<Word> findWordsByLetters(String letters, Integer num);

    public List<Word> findWordsByParams(String letters, Integer num);

    public int addWords(WordVo wordVo);

    public WordWrapper findOne(String word);

    public WordWrapper findOneRemote(String word);
}
