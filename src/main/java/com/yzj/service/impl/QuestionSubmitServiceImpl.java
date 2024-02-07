package com.yzj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzj.common.ErrorCode;
import com.yzj.exception.BusinessException;
import com.yzj.gudge.JudgeService;
import com.yzj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yzj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yzj.model.entity.Question;
import com.yzj.model.entity.QuestionSubmit;
import com.yzj.model.entity.User;
import com.yzj.model.enums.QuestionSubmitLanguageEnum;
import com.yzj.model.enums.QuestionSubmitStatusEnum;
import com.yzj.model.vo.QuestionSubmitVO;
import com.yzj.service.QuestionService;
import com.yzj.service.QuestionSubmitService;
import com.yzj.mapper.QuestionSubmitMapper;
import com.yzj.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @author 姚浙杰
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2024-02-06 13:11:28
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private JudgeService judgeService;

    /**
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        //校验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"编程语言错误！");
        }
        Long questionId = questionSubmitAddRequest.getQuestionId();
        //判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null){
            throw  new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已经提交题目
        Long userId = loginUser.getId();
        //每个用户串行提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);
        //设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据插入失败！");
        }
        Long questionSubmitId = questionSubmit.getId();
        //执行判题服务
        CompletableFuture.runAsync(() -> {
            judgeService.doJudge(questionSubmitId);
        });
        return 0;
    }

    /**
     * 获取查询包装类（用户根据哪些字段查询，根据前端传来的请求对象，得到 mybatis 框架支持的查询 QueryWrapper 类）
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        return null;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        return null;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        return null;
    }
}




