package com.zmlh.controller;

import com.zmlh.check.annotation.admin.UserCheck;
import com.zmlh.entity.Pwd;
import com.zmlh.entity.Response;
import com.zmlh.server.PassWordServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName PassWordController
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-12 15:28
 * @Version 1.0
 **/
@RestController
@Slf4j
@RequestMapping("/zmlh/password")
public class PassWordController {
    @Autowired
    private PassWordServer passWordServer;

    @PostMapping("/update")
    public Response updatePwd ( @Validated @RequestBody Pwd pwd ) {
        return passWordServer.update(pwd);
    }

    @PutMapping("/reset/{userId}/{login}")
    public Response restPwd ( @UserCheck @PathVariable String login, @PathVariable String userId ) {
        log.info("重置密码的人是：" + login);
        return passWordServer.restPwd(userId);
    }
}
