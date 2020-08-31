package com.study.seata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.base.common.ResultMsg;
import com.study.seata.model.User;

public interface UserService extends IService<User> {


    ResultMsg add(User user);

}
