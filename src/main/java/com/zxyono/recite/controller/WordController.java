package com.zxyono.recite.controller;

import com.zxyono.recite.entity.vo.WordVo;
import com.zxyono.recite.entity.wrapper.WordWrapper;
import com.zxyono.recite.service.WordService;
import com.zxyono.recite.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "word")
public class WordController {
    @Autowired
    private WordService wordService;

    /**
     * 根据首字母 / 需要的单词个数获取单词
     */
    @RequestMapping(value = "/search/letters/{letter}")
    public ResultMap searchByFirstLetter(@PathVariable("letter") String letter,
                                         @RequestParam(name = "num") String num,
                                         @RequestParam(name = "type", defaultValue = "1") Integer type) {
        int paramNum = 20;
        if (num != null) {
            if (num.equals("all")) {
                paramNum = -1;
            } else {
                paramNum = Integer.parseInt(num);
            }
        }

        return ResultMap.success(wordService.findWordsByParamsRegexLimit(type, letter, paramNum));
    }

    /**
     * 根据输入的前n个字母查询单词
     */
    @RequestMapping(value = "/search/{letters}")
    public ResultMap searchByLetters(@PathVariable("letters") String letters,
                                     @RequestParam(name = "num") String num,
                                     @RequestParam(name = "type", defaultValue = "1") Integer type) {
        int paramNum = 20;
        if (num != null) {
            if (num.equals("all")) {
                paramNum = -1;
            } else {
                paramNum = Integer.parseInt(num);
            }
        }
        return ResultMap.success(wordService.findWordsByLetters(type, letters, paramNum));
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

    /**
     * 获取热门单词
     * @param num
     * @return
     */
    @RequestMapping(value = "/hot/{num}")
    public ResultMap getHotWords(@PathVariable("num") int num) {
        return ResultMap.success(wordService.findHotWords(num));
    }

    /**
     * 百度语音合成API
     */
    @RequestMapping(value = "/audio/{per}/{word}")
    public String getAudio(@PathVariable("per") int per, @PathVariable("word") String word, HttpServletResponse response) {
        return wordService.getAudio(word, per);
    }

}