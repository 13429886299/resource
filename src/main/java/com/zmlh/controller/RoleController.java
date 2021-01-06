package com.zmlh.controller;

import com.zmlh.entity.ResRoleTab;
import com.zmlh.entity.Response;
import com.zmlh.server.RoleServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 11:03
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/zmlh/role")
public class RoleController {
    @Autowired
    private RoleServer roleServer;

    @GetMapping("/select")
    public Response getAll () {
        log.info("获取所有的角色信息！");
        return roleServer.getAll();
    }

    @GetMapping("/select/{id}")
    public Response getRoleById (@Validated @NotNull @PathVariable String id) {
        log.info("获取所有的角色信息！");
        return roleServer.getAllById(id);
    }

    @PostMapping("/insert")
    public Response insert ( @Validated @RequestBody ResRoleTab resRoleTab ) {
        log.info("插入或者更新的角色信息！");
        return roleServer.insert(resRoleTab);
    }

    @DeleteMapping("/delete/{id}")
    public Response delete ( @Validated @NotNull @PathVariable String id ) {
        log.info("删除角色信息！");
        return roleServer.delete(id);
    }
}
