package com.zxyono.recite.service.impl;

import com.zxyono.recite.entity.Word;
import com.zxyono.recite.repository.WordRepository;
import com.zxyono.recite.service.WordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {
    @Resource
    private WordRepository wordRepository;

    @Override
    public List<Word> findWordsByFirstLetter(String letter) {
        return wordRepository.queryAllByFirstLetter(letter);
    }
}