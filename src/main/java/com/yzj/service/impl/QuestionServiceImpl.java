package com.yzj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzj.model.entity.Question;
import com.yzj.service.QuestionService;
import com.yzj.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 姚浙杰
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2023-11-16 12:56:50
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




