package com.zxyono.recite.controller;

import com.zxyono.recite.service.AnswerService;
import com.zxyono.recite.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @RequestMapping(value = "get")
    public ResultMap getAnswerById(@Param("answerId") Long answerId) {
        return ResultMap.success(answerService.findAnswerById(answerId));
    }

}