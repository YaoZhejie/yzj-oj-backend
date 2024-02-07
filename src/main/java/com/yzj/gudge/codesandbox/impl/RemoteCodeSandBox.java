package com.yzj.gudge.codesandbox.impl;

import com.yzj.gudge.codesandbox.CodeSandbox;
import com.yzj.gudge.codesandbox.model.ExecuteCodeRequest;
import com.yzj.gudge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱
 */
public class RemoteCodeSandBox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
