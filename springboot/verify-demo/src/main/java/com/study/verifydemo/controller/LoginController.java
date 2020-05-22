package com.study.verifydemo.controller;

import com.study.verifydemo.common.ResultMsg;
import com.study.verifydemo.common.ResultStatusCode;
import com.study.verifydemo.filter.ApiIdempotent;
import com.study.verifydemo.model.User;
import com.study.verifydemo.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Milogenius
 * @create: 2019-07-08 10:36
 * @description:
 **/
@Slf4j
@RestController
public class LoginController {
    public static  List<Integer> ids =new ArrayList<Integer>();

    static {
        for (int i = 0; i < 10; i++) {
            ids.add(i);
        }
    }

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResultMsg login (String loginName,String passWord){
        if(StringUtils.isBlank(loginName) || StringUtils.isBlank(passWord)){
            return new ResultMsg(ResultStatusCode.NO_DATA,null);
        }
        //身份验证
        //返回token
        return tokenService.createToken(new User(loginName, passWord));
    }

    @PostMapping("/getUser")
    public ResultMsg getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        String name = request.getHeader("name");
        if(StringUtils.isBlank(token) || StringUtils.isBlank(name)){
            return new ResultMsg(ResultStatusCode.NO_DATA,null);
        }
        ResultMsg resultMsg = tokenService.checkToken(token, name);
        if (200==resultMsg.getCode()) {
            return new ResultMsg(ResultStatusCode.OK,"鉴权成功");
        }
        return new ResultMsg(ResultStatusCode.NO_DATA,"鉴权失败");
    }

    @ApiIdempotent
    @PostMapping("/test")
    public ResultMsg test(int id){
        boolean boo = ids.add(id);
        if (boo) {
            return new ResultMsg(ResultStatusCode.OK,ids);
        }
        return new ResultMsg(ResultStatusCode.SYSTEM_ERR,"新增失败");
    }
}
