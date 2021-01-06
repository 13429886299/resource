package com.zmlh.controller;

import com.zmlh.entity.Response;
import com.zmlh.server.ResourceServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class ResourceController {
    @Autowired
    private ResourceServer resourceServer;

    @GetMapping("/student")
    public Response getAllStudent () {
        return resourceServer.getAll();
    }


}
