package com.zmlh.controller;

import com.zmlh.entity.DictAllInfo;
import com.zmlh.entity.DictInfo;
import com.zmlh.entity.Response;
import com.zmlh.server.DictServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @ClassName DictController
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 16:45
 * @Version 1.0
 **/
@RestController
@Slf4j
@RequestMapping("/zmlh/dict/all")
public class DictController {
    @Autowired
    private DictServer dictServer;

    @GetMapping("/select")
    @ResponseBody
    public Response getAllDict () {
        log.info("开始获取系统所有的字典表信息");
        return dictServer.getAll();
    }

    @PostMapping("/insert")
    public Response insert (@Validated @RequestBody DictInfo dictInfo ) {
        log.info("开始插入新的大字典信息！");
        return dictServer.insert(dictInfo);
    }

    @PostMapping("/value/insert")
    public Response insert ( @Validated @RequestBody DictAllInfo dictAllInfo ) {
        log.info("开始插入新的小字典信息！");
        return dictServer.insert(dictAllInfo);
    }

    @DeleteMapping("/delete/{id}")
    public Response deleteBig (@Validated @PathVariable @NotNull String id ) {
        log.info("开始删除的大字典信息！");
        return dictServer.deleteBig(id);
    }

    @DeleteMapping("/value/delete/{id}")
    public Response deleteSmall (@Validated @PathVariable @NotNull String id ) {
        log.info("开始删除的小字典信息！");
        return dictServer.deleteSmall(id);
    }
}
