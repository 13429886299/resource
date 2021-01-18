package com.zmlh.controller;

import com.zmlh.dictionary.ExcelDictionary;
import com.zmlh.entity.Response;
import com.zmlh.entity.ScheduleTimeTab;
import com.zmlh.server.ScheduleTimeServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * @ClassName ScheduleTimeController
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-13 15:24
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/zmlh/schedule")
@Validated
public class ScheduleTimeController {
    @Autowired
    private ScheduleTimeServer scheduleTimeServer;

    @GetMapping("/select")
    public Response getAll () {
        log.info("获取所有的排课时间安排");
        return scheduleTimeServer.getAll();
    }

    @PostMapping("/insert")
    public Response insert ( @RequestBody ScheduleTimeTab scheduleTimeTab ) {
        return scheduleTimeServer.insert(scheduleTimeTab);
    }

    @DeleteMapping("/{id}")
    public Response insert ( @PathVariable String id ) {
        return scheduleTimeServer.delete(id);
    }

    @PostMapping(value = "/excel/{season}/{time}")
    public Response insertExcel ( @NotNull @RequestBody MultipartFile file, @PathVariable String season, @PathVariable @FutureOrPresent(message = ExcelDictionary.FUTURE_PRESENT) Instant time ) {
        log.info("开始上传Excel课表");
        return scheduleTimeServer.insertExcel(file, season, time);
    }

    @GetMapping(value = "/excel/{season}")
    public void getModelExcel ( @PathVariable String season, HttpServletResponse response ) {
        log.info("开始上传");
        scheduleTimeServer.getModelExcel(season, response);
    }

}
