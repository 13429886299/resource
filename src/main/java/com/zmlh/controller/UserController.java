package com.zmlh.controller;

import com.zmlh.entity.ResUserTab;
import com.zmlh.entity.Response;
import com.zmlh.server.UserServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 16:55
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/zmlh/user")
public class UserController {

    @Autowired
    private UserServer userServer;

    @GetMapping("/select")
    public Response getAll () {
        log.info("获取所有的可登陆用户信息！");
        return userServer.getAll();
    }

    @GetMapping("/select/{id}")
    public Response getRoleById ( @Validated @NotNull @PathVariable String id ) {
        log.info("根据ID获取用户信息！");
        return userServer.getAllById(id);
    }

    @PostMapping("/insert")
    public Response insert ( @Validated @RequestBody ResUserTab resUserTab ) {
        log.info("插入新的用户信息！");
        return userServer.insert(resUserTab);
    }


    @DeleteMapping("/delete/{id}")
    public Response delete ( @Validated @NotNull @PathVariable String id ) {
        log.info("删除用户信息！");
        return userServer.delete(id);
    }
}
