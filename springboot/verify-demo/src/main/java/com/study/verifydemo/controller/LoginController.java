package com.study.verifydemo.controller;

import com.study.base.common.ResultMsg;
import com.study.base.common.ResultStatusCode;
import com.study.verifydemo.annotation.ApiIdempotent;
import com.study.verifydemo.annotation.CacheLock;
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
 * @Author luoshangcai
 * @Description //TODO  redis+token 实现接口幂等性
 * @Date 17:32 2020-05-25
 * @Param 
 * @return 
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
    @ApiIdempotent(generateToken=true)
    public ResultMsg login (String loginName, String passWord){
        if(StringUtils.isBlank(loginName) || StringUtils.isBlank(passWord)){
            return new ResultMsg(ResultStatusCode.NO_DATA,null);
        }
        //身份验证
        //返回token
//        return tokenService.createToken(new User(loginName, passWord));
        return new ResultMsg(ResultStatusCode.OK,"在返回头中返回token");
    }

    @PostMapping("/getUser")
    public ResultMsg getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        String name = request.getHeader("name");
        if(StringUtils.isBlank(token) || StringUtils.isBlank(name)){
            return new ResultMsg(ResultStatusCode.NO_DATA,null);
        }
        ResultMsg resultMsg = tokenService.checkToken(token);
        if (200==resultMsg.getCode()) {
            return new ResultMsg(ResultStatusCode.OK,"鉴权成功");
        }
        return new ResultMsg(ResultStatusCode.NO_DATA,"鉴权失败");
    }

    @PostMapping("/test")
    @ApiIdempotent(removeToken=true)
    public ResultMsg test(int id){
        boolean boo = ids.add(id);
        if (boo) {
            
            return new ResultMsg(ResultStatusCode.OK,ids);
        }
        return new ResultMsg(ResultStatusCode.SYSTEM_ERR,"新增失败");
    }


    @PostMapping("/redisTest")
    @CacheLock(prefix="redisTest",expire=60)
    public ResultMsg redisTest(int id){
        boolean boo = ids.add(id);
        if (boo) {
            return new ResultMsg(ResultStatusCode.OK,ids);
        }
        return new ResultMsg(ResultStatusCode.SYSTEM_ERR,"新增失败");
    }

    /**
     * @Description //TODO  分布式方法锁
     *                  方法限流 限制  1秒只能调用180次 设置的值应小于服务限流
     * @Param []
     * @return java.lang.String
     **/
//    @RequestMapping("/cache/check")
//    @DistributedLock(lock= LockType.METHOD,timeOut = 15000)
//    @RequestLimit(limit = 180,period =1 )
//    public String cacheCheck(int id){
//        ids.add(id);
//        return JSON.toJSONString(ids);
//    }
}
