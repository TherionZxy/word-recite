package com.zxyono.recite.service;

import com.zxyono.recite.entity.Word;
import com.zxyono.recite.entity.vo.WordVo;
import com.zxyono.recite.entity.wrapper.WordWrapper;

import java.util.List;
import java.util.Map;

public interface WordService {
    public List<Word> findWordsByLetters(Integer type, String letters, Integer num);

    public List<Word> findWordsByParams(Integer type, String letters, Integer num);

    public List<Word> findWordsByParamsRegexLimit(Integer type, String letters, Integer num);

    public int addWords(WordVo wordVo);

    public WordWrapper findOne(String word);

    public WordWrapper findOneRemote(String word);

    public List<String> findHotWords(Integer num);

    public String getAudio(String word, int per);
}
