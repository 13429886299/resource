package com.zmlh.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

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

    @GetMapping("/select/{pageNo}/{pageSize}/{time}")
    @ResponseBody
    public Response getBaseSharePage ( @PathVariable int pageNo, @PathVariable int pageSize, @PathVariable Instant time ) {
        log.info("开始分页获取排课安排信息");
        return scheduleTimeServer.getPage(pageNo, pageSize, time);
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

    @GetMapping(value = "/excel/{time}")
    public void getModelExcel ( @PathVariable @NotNull long time, HttpServletResponse response ) {
        log.info("开始下载Excel文件");
        scheduleTimeServer.getModelExcel(time, response);
    }


}
