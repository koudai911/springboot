package com.study.elasticjob.controller;

import com.alibaba.fastjson.JSON;
import com.study.base.common.ResultMsg;
import com.study.base.common.ValidateResult;
import com.study.base.group.insert;
import com.study.base.util.ValidateUtil;
import com.study.elasticjob.mapper.TestMapper;
import com.study.elasticjob.model.Ttest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 用户controller
 * @Author: luoshangcai
 * @Date 2020-08-15 17:10
 **/
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @RequestMapping("/add")
    public ResultMsg add(@RequestParam("id") Integer id) {
        Ttest test = new Ttest(id);
        ValidateResult validateResult = ValidateUtil.validateBean(test, insert.class);
        if (validateResult.hasErrors()) {
            return ResultMsg.createByErrorMessage(JSON.toJSONString(validateResult.getAllErrors()));
        }

        testMapper.insert(test);

        return ResultMsg.createBySuccess();
    }
}
