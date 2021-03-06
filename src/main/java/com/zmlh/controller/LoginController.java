package com.zmlh.controller;

import com.alibaba.fastjson.JSON;
import com.zmlh.entity.LoginInfo;
import com.zmlh.entity.Response;
import com.zmlh.server.LoginServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 19:53
 * @Version 1.0
 **/
@RestController
@Slf4j
@RequestMapping("/zmlh")
public class LoginController {
    @Autowired
    private LoginServer loginServer;

    @PostMapping("/login")
    public Response login ( @Validated @RequestBody LoginInfo loginInfo ) {
        log.info("登陆的用户名是：" + JSON.toJSONString(loginInfo));
        return loginServer.login(loginInfo);
    }


}
