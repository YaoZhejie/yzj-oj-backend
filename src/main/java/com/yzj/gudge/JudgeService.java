package com.yzj.gudge;


import com.yzj.model.entity.QuestionSubmit;

public interface JudgeService {
    /**
     * 判题
     *
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
