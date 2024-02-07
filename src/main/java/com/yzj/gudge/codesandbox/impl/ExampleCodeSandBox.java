package com.yzj.gudge.codesandbox.impl;

import com.yzj.gudge.codesandbox.CodeSandbox;
import com.yzj.gudge.codesandbox.model.ExecuteCodeRequest;
import com.yzj.gudge.codesandbox.model.ExecuteCodeResponse;
import com.yzj.gudge.codesandbox.model.JudgeInfo;
import com.yzj.model.enums.JudgeInfoMessageEnum;
import com.yzj.model.enums.QuestionSubmitStatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 示例代码沙箱
 */
@Slf4j
public class ExampleCodeSandBox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {

        List<String> inputList = request.getInputList();
        String code = request.getCode();
        String language = request.getLanguage();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemopry(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
