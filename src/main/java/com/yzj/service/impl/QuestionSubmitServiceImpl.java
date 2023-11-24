package com.yzj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzj.common.ErrorCode;
import com.yzj.exception.BusinessException;
import com.yzj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yzj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yzj.model.entity.QuestionSubmit;
import com.yzj.model.entity.User;
import com.yzj.model.vo.QuestionSubmitVO;
import com.yzj.service.QuestionSubmitService;
import com.yzj.mapper.QuestionSubmitMapper;
import org.springframework.stereotype.Service;


/**
* @author 姚浙杰
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2023-11-16 13:18:16
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{


    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        return 0;
    }

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

    @Override
    public int doQuestionSubmit(long questionId, User loginUser) {
        return 0;
    }

    @Override
    public int doQuestionSubmitInner(long userId, long questionId) {
        return 0;
    }
}




