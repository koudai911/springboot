package com.study.verifydemo.service.impl;

import com.study.verifydemo.common.Constant;
import com.study.verifydemo.common.ResultMsg;
import com.study.verifydemo.common.ResultStatusCode;
import com.study.verifydemo.model.User;
import com.study.verifydemo.service.TokenService;
import com.study.verifydemo.utils.JwtUtil;
import com.study.verifydemo.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
public class TokenServiceImpls implements TokenService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TokenService tokenService;

    @Override
    public ResultMsg createToken(User user) {
        String token = JwtUtil.sign(user.getName(), user.getPassword());
        if (token != null) {
            // 将token存到redis中
            redisUtils.set(user.getName(), token, Constant.EXPIRE_TIME_MINUTE);
            return new ResultMsg(ResultStatusCode.OK,token);
        }
        return new ResultMsg(ResultStatusCode.NO_DATA,null);
    }

    @Override
    public ResultMsg checkToken(String token) {
        // header中不存在token
        if (StringUtils.isBlank(token)) {
            return new ResultMsg(40010,"参数不合法，必须带token参数",null);
        }
        if (!redisUtils.exists(token)) {
            return new ResultMsg(40010,"请不要重复提交",null);
        }
        Collection<String> keys =new ArrayList();
        keys.add(token);
        Long del=redisUtils.delete(keys);
        if(del<1){
            //redis 单线程 并发下防止重复提交
            return new ResultMsg(40010,"请勿重复操作",null);
        }
        return new ResultMsg(ResultStatusCode.OK,"检验token成功");
    }
}
