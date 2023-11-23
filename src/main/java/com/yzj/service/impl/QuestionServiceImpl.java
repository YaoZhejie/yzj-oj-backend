package com.yzj.service.impl;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.yzj.common.ErrorCode;
import com.yzj.constant.CommonConstant;
import com.yzj.exception.BusinessException;
import com.yzj.exception.ThrowUtils;
import com.yzj.model.dto.question.QuestionQueryRequest;
import com.yzj.model.entity.*;

import com.yzj.model.vo.QuestionVO;
import com.yzj.model.vo.UserVO;
import com.yzj.service.QuestionService;
import com.yzj.mapper.QuestionMapper;
import com.yzj.service.UserService;
import com.yzj.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 姚浙杰
* @description 针对表【Question(题目)】的数据库操作Service实现
* @createDate 2023-11-16 12:56:50
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{
    private final static Gson GSON = new Gson();

    @Resource
    private UserService userService;



    @Override
    public void validQuestion(Question Question, boolean add) {
        if (Question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = Question.getTitle();
        String content = Question.getContent();
        String tags = Question.getTags();
        String answer = Question.getAnswer();
        String judgeCase = Question.getJudgeCase();
        String judgeConfig = Question.getJudgeConfig();

        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, content, tags), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
        if (StringUtils.isNotBlank(answer) && answer.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
        if (StringUtils.isNotBlank(judgeCase) && judgeCase.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "判断用例过长");
        }
        if (StringUtils.isNotBlank(judgeConfig) && judgeConfig.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "判断配置过长");
        }
    }

    /**
     * 获取查询包装类（根据前端传来的对象，得到mybatis框架支持的语言）
     *
     * @param questionQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if (questionQueryRequest == null) {
            return queryWrapper;
        }

        Long id = questionQueryRequest.getId();
        String title = questionQueryRequest.getTitle();
        String content = questionQueryRequest.getContent();
        List<String> tags = questionQueryRequest.getTags();
        String answer = questionQueryRequest.getAnswer();
        Long userId = questionQueryRequest.getUserId();
        String sortField = questionQueryRequest.getSortField();
        String sortOrder = questionQueryRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.like(StringUtils.isNotBlank(answer), "answer", answer);
        if (CollectionUtils.isNotEmpty(tags)) {
            for (String tag : tags) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }

        queryWrapper.ne(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }



    @Override
    public QuestionVO getQuestionVO(Question question, HttpServletRequest request) {
        QuestionVO questionVO = QuestionVO.objToVo(question);
        long QuestionId = question.getId();
        // 1. 关联查询用户信息
        Long userId = question.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        questionVO.setUserVO(userVO);

        return questionVO;
    }

    @Override
    public Page<QuestionVO> getQuestionVOPage(Page<Question> QuestionPage, HttpServletRequest request) {
        List<Question> QuestionList = QuestionPage.getRecords();
        Page<QuestionVO> QuestionVOPage = new Page<>(QuestionPage.getCurrent(), QuestionPage.getSize(), QuestionPage.getTotal());
        if (CollectionUtils.isEmpty(QuestionList)) {
            return QuestionVOPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = QuestionList.stream().map(Question::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 2. 已登录，获取用户点赞、收藏状态
        Map<Long, Boolean> QuestionIdHasThumbMap = new HashMap<>();
        Map<Long, Boolean> QuestionIdHasFavourMap = new HashMap<>();
        User loginUser = userService.getLoginUserPermitNull(request);
        if (loginUser != null) {
            Set<Long> QuestionIdSet = QuestionList.stream().map(Question::getId).collect(Collectors.toSet());
            loginUser = userService.getLoginUser(request);
            // 获取点赞
            QueryWrapper<QuestionThumb> QuestionThumbQueryWrapper = new QueryWrapper<>();
            QuestionThumbQueryWrapper.in("QuestionId", QuestionIdSet);
            QuestionThumbQueryWrapper.eq("userId", loginUser.getId());
            List<QuestionThumb> QuestionQuestionThumbList = QuestionThumbMapper.selectList(QuestionThumbQueryWrapper);
            QuestionQuestionThumbList.forEach(QuestionQuestionThumb -> QuestionIdHasThumbMap.put(QuestionQuestionThumb.getQuestionId(), true));
            // 获取收藏
            QueryWrapper<QuestionFavour> QuestionFavourQueryWrapper = new QueryWrapper<>();
            QuestionFavourQueryWrapper.in("QuestionId", QuestionIdSet);
            QuestionFavourQueryWrapper.eq("userId", loginUser.getId());
            List<QuestionFavour> QuestionFavourList = QuestionFavourMapper.selectList(QuestionFavourQueryWrapper);
            QuestionFavourList.forEach(QuestionFavour -> QuestionIdHasFavourMap.put(QuestionFavour.getQuestionId(), true));
        }
        // 填充信息
        List<QuestionVO> QuestionVOList = QuestionList.stream().map(Question -> {
            QuestionVO QuestionVO = QuestionVO.objToVo(Question);
            Long userId = Question.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            QuestionVO.setUser(userService.getUserVO(user));
            QuestionVO.setHasThumb(QuestionIdHasThumbMap.getOrDefault(Question.getId(), false));
            QuestionVO.setHasFavour(QuestionIdHasFavourMap.getOrDefault(Question.getId(), false));
            return QuestionVO;
        }).collect(Collectors.toList());
        QuestionVOPage.setRecords(QuestionVOList);
        return QuestionVOPage;
    }

}




