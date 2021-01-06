package com.zmlh.controller;

import com.zmlh.entity.ResNodeTab;
import com.zmlh.entity.Response;
import com.zmlh.server.ResNodeTabServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ResNodeController
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 20:12
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/zmlh/node/class")
public class ResNodeController {

    @Autowired
    private ResNodeTabServer resNodeTabServer;

    @GetMapping("/select")
    public Response getAll () {
        log.info("获取所有的学生信息！");
        return resNodeTabServer.getAll();
    }

    @PostMapping("/insert")
    public Response insert ( @Validated @RequestBody ResNodeTab resNodeTab ) {
        log.info("保存或者更新班级名称！");
        return resNodeTabServer.insert(resNodeTab);
    }

    @Delete("/{id}")
    public Response delete ( @Validated @PathVariable String id ) {
        log.info("开始删除班级信息！");
        return resNodeTabServer.delete(id);
    }
}
