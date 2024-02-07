package com.yzj.gudge;

import com.yzj.model.entity.QuestionSubmit;
import com.yzj.service.QuestionService;
import com.yzj.service.QuestionSubmitService;

import javax.annotation.Resource;

public class JudgeServiceImpl implements JudgeService{
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {

        return null;
    }
}
