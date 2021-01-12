package com.zmlh.controller;

import com.zmlh.check.annotation.admin.UserCheck;
import com.zmlh.entity.Response;
import com.zmlh.server.RecordLessonServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName RecordLessonController
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-08 17:26
 * @Version 1.0
 **/
@RestController
@RequestMapping("/zmlh/record")
@Slf4j
@Validated
public class RecordLessonController {
    @Autowired
    private RecordLessonServer recordLessonServer;

    @GetMapping("/all")
    public Response getAll () {
        return recordLessonServer.getAll();
    }

    @GetMapping("/{id}")
    public Response getById ( @PathVariable String id ) {
        log.info("开始获取ID " + id + " 的消课记录");
        return recordLessonServer.getAllById(id);
    }

    @DeleteMapping("/{id}/{userId}")
    public Response delete ( @PathVariable String id, @PathVariable @UserCheck String userId ) {
        log.info("开始删除已有记录，删除用户是：" + userId);
        return recordLessonServer.delete(id);
    }

}
