package com.zmlh.controller;

import com.zmlh.entity.Response;
import com.zmlh.server.RecordLessonServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
