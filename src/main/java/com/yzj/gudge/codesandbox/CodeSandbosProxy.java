package com.yzj.gudge.codesandbox;

import com.yzj.gudge.codesandbox.model.ExecuteCodeRequest;
import com.yzj.gudge.codesandbox.model.ExecuteCodeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class CodeSandbosProxy implements CodeSandbox {

    private final CodeSandbox codeSandbox;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        log.info("代码沙箱示例信息" + request.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(request);
        log.info("代码沙箱响应信息" + executeCodeResponse.toString());
        return codeSandbox.executeCode(request);

    }
}
