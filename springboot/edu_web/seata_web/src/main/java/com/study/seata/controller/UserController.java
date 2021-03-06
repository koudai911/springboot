package com.study.seata.controller;

import com.alibaba.fastjson.JSON;
import com.study.base.common.ResultMsg;
import com.study.base.common.ValidateResult;
import com.study.base.group.insert;
import com.study.base.util.ValidateUtil;
import com.study.seata.model.User;
import com.study.seata.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 用户controller
 * @Author: luoshangcai
 * @Date 2020-08-15 17:10
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/set")
    public ResultMsg set(User user) {
        ValidateResult validateResult = ValidateUtil.validateBean(user, insert.class);
        if (validateResult.hasErrors()) {
            return ResultMsg.createByErrorMessage(JSON.toJSONString(validateResult.getAllErrors()));
        }
        return ResultMsg.createBySuccess(user);
    }

    @RequestMapping("/add")
    public ResultMsg add(User user) {
        ValidateResult validateResult = ValidateUtil.validateBean(user, insert.class);
        if (validateResult.hasErrors()) {
            return ResultMsg.createByErrorMessage(JSON.toJSONString(validateResult.getAllErrors()));
        }
        return userService.add(user);
    }
}
