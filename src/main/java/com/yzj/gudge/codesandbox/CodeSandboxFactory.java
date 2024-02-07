package com.yzj.gudge.codesandbox;

import com.yzj.gudge.codesandbox.impl.ExampleCodeSandBox;
import com.yzj.gudge.codesandbox.impl.RemoteCodeSandBox;
import com.yzj.gudge.codesandbox.impl.ThirdPartyCodeSandBox;

/**
 *代码沙箱工厂
 */
public class CodeSandboxFactory {
    /**
     * 创建代码沙箱示例
     * @param type 沙箱类型
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandBox();
            case "remote":
                return new RemoteCodeSandBox();
            case "thirdParty":
                return new ThirdPartyCodeSandBox();
            default:
                return new ExampleCodeSandBox();
        }
    }
}
