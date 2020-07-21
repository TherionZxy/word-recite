package com.zxyono.recite.service.impl;

import com.zxyono.recite.repository.ScoresRepository;
import com.zxyono.recite.service.ScoresService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ScoresServiceImpl implements ScoresService {
    @Resource
    private ScoresRepository scoresRepository;


}