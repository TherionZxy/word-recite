package com.zxyono.recite.controller;

import com.zxyono.recite.entity.vo.WordVo;
import com.zxyono.recite.entity.wrapper.WordWrapper;
import com.zxyono.recite.service.WordService;
import com.zxyono.recite.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "word")
public class WordController {
    @Autowired
    private WordService wordService;

    /**
     * 根据首字母 / 需要的单词个数获取单词
     */
    @RequestMapping(value = "/search/letters/{letter}")
    public ResultMap searchByFirstLetter(@PathVariable("letter") String letter, @Param("num") Integer num) {
        if (num == null) {
            num = 20;
        }
        return ResultMap.success(wordService.findWordsByParams(letter, num));
    }

    /**
     * 根据输入的前n个字母查询单词
     */
    @RequestMapping(value = "/search/{letters}")
    public ResultMap searchByLetters(@PathVariable("letters") String letters, @Param("num") Integer num) {
        return ResultMap.success(wordService.findWordsByLetters(letters, num));
    }

    /**
     * 精确查询单词
     * @param word
     * @return
     */
    @RequestMapping(value = "/get/{word}")
    public ResultMap searchByEn(@PathVariable("word") String word) {
        WordWrapper wordWrapper = wordService.findOne(word);
        if (wordWrapper == null) {
            return ResultMap.error(201, "未查询到该单词");
        }
        return ResultMap.success(wordWrapper);
    }

    @RequestMapping(value = "/remote/{word}")
    public ResultMap remoteSearchByEn(@PathVariable("word") String word) {
        WordWrapper wordWrapper = wordService.findOneRemote(word);
        if (wordWrapper == null) {
            return ResultMap.error(201, "未查询到该单词");
        }
        return ResultMap.success(wordWrapper);
    }

    /**
     * 添加单词
     * @param wordVo
     * @return
     */
    @RequestMapping(value = "/add")
    public ResultMap addNewWords(@RequestBody WordVo wordVo) {
        return ResultMap.success(wordService.addWords(wordVo));
    }

}