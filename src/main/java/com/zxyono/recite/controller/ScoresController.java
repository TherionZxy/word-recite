package com.zxyono.recite.controller;

import com.zxyono.recite.entity.vo.Submit;
import com.zxyono.recite.service.ScoresService;
import com.zxyono.recite.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "scores")
public class ScoresController {
    @Autowired
    private ScoresService scoresService;

    /**
     * 获取某条答题记录
     * @param scoresId
     * @return
     */
    @RequestMapping(value = "get")
    public ResultMap getScoresById(@Param("scoresId") Long scoresId) {
        return ResultMap.success(scoresService.findScoresById(scoresId));
    }

    /**
     * 提交答卷
     * @return
     */
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    public ResultMap submitScores(@RequestBody Submit submit) {
        return ResultMap.success(scoresService.addScores(submit));
    }

    /**
     * 获取全部答题记录
     * @return
     */
    @RequestMapping(value = "getall")
    public ResultMap getAllScores(@Param("page") int page, @Param("size") int size) {
        return ResultMap.success(scoresService.findAll(page, size));
    }
}