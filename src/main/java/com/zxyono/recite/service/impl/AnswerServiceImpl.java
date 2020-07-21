package com.zxyono.recite.service.impl;

import com.zxyono.recite.repository.AnswerRepository;
import com.zxyono.recite.service.AnswerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Resource
    private AnswerRepository answerRepository;


}