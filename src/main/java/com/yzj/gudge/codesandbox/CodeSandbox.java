package com.yzj.gudge.codesandbox;


import com.yzj.gudge.codesandbox.model.ExecuteCodeRequest;
import com.yzj.gudge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest request);
}
