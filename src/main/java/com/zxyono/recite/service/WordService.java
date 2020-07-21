package com.zxyono.recite.service;

import com.zxyono.recite.entity.Word;

import java.util.List;

public interface WordService {
    public List<Word> findWordsByFirstLetter(String letter);
}
