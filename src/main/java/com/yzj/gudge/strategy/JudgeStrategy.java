package com.yzj.gudge.strategy;

import com.yzj.gudge.codesandbox.model.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
