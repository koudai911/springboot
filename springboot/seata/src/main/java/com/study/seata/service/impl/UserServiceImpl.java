package com.study.seata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.base.common.ResultMsg;
import com.study.seata.feignapi.MinioFeign;
import com.study.seata.mapper.UserMapper;
import com.study.seata.model.User;
import com.study.seata.service.UserService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: luoshangcai
 * @Date 2020-08-19 19:07
 **/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MinioFeign minioFeign;

    @Override
    @GlobalTransactional
    public ResultMsg add(User user) {

        int number = userMapper.insert(user);

        if(number>0){
            ResultMsg msg = minioFeign.add(user.getId(), user.getAge());
            if(200 !=msg.getCode()){
                throw new RuntimeException(msg.getMsg());
            }
        }else{
            return ResultMsg.createByError();
        }

        return ResultMsg.createBySuccess();
    }
}
