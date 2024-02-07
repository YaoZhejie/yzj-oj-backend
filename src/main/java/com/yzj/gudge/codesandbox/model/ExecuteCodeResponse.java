package com.yzj.gudge.codesandbox.model;

import lombok.Data;

import java.util.List;

/**
 * 代码执行返回类
 */
@Data
public class ExecuteCodeResponse {

    /**
     * 输出返回列表
     */
    private List<String> outputList;

    /**
     *
     * 接口信息
     */
    private String message;

    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

}
