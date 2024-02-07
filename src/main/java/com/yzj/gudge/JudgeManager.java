package com.yzj.gudge;


import com.yzj.gudge.codesandbox.model.JudgeInfo;
import com.yzj.gudge.strategy.DefaultJudgeStrategy;
import com.yzj.gudge.strategy.JavaLanguageJudgeStrategy;
import com.yzj.gudge.strategy.JudgeContext;
import com.yzj.gudge.strategy.JudgeStrategy;
import com.yzj.model.entity.QuestionSubmit;

/**
 * 判题管理（简化调用）
 */
public class JudgeManager {
    /**
     * 执行判题
     */
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
