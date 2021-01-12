package com.zmlh.controller;

import com.zmlh.entity.Response;
import com.zmlh.entity.StudentInfoTab;
import com.zmlh.server.ResourceServer;
import com.zmlh.server.StudentServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ResourceController
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 18:03
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/zmlh/resource")
@Validated
public class ResourceController {
    @Autowired
    private ResourceServer resourceServer;
    @Autowired
    private StudentServer studentServer;

    @GetMapping("/student")
    public Response getAllStudent () {
        return resourceServer.getAll();
    }

    @PostMapping("/student")
    public Response insert ( @RequestBody StudentInfoTab studentInfo ) {
        return studentServer.insert(studentInfo);
    }

    @DeleteMapping("/{id}")
    public Response delete ( @PathVariable String id ) {
        return studentServer.delete(id);
    }
}
