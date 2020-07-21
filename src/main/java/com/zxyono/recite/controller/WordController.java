package com.zxyono.recite.controller;

import com.zxyono.recite.service.WordService;
import com.zxyono.recite.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "word")
public class WordController {
    @Autowired
    private WordService wordService;

    @RequestMapping(value = "/search/letter/{letter}")
    public ResultMap searchByFirstLetter(@PathVariable("letter") String letter) {
        return ResultMap.success(wordService.findWordsByFirstLetter(letter));
    }



}