package com.zmlh.server.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zmlh.dictionary.ExcelDictionary;
import com.zmlh.entity.ExcelFile;
import com.zmlh.entity.Response;
import com.zmlh.entity.ScheduleTimeTab;
import com.zmlh.mapper.ScheduleTimeMapper;
import com.zmlh.server.ScheduleTimeServer;
import com.zmlh.until.ExcelReadListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * @ClassName ScheduleTimeServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-13 15:25
 * @Version 1.0
 **/
@Service
@Slf4j
public class ScheduleTimeServerImpl implements ScheduleTimeServer {
    @Autowired
    private ScheduleTimeMapper scheduleTimeMapper;

    @Override
    public Response getAll () {
        return new Response().setCode(200).setObject(scheduleTimeMapper.selectList(new QueryWrapper<>()));
    }

    @Override
    public Response getAllById ( String id ) {
        return null;
    }

    @Override
    public Response insert ( ScheduleTimeTab scheduleTimeTab ) {
        return insert(scheduleTimeMapper, scheduleTimeTab, scheduleTimeTab.getId());
    }

    @Override
    public Response update ( ScheduleTimeTab scheduleTimeTab ) {
        scheduleTimeMapper.updateById(scheduleTimeTab);
        return new Response().setCode(200).setObject(scheduleTimeTab.getId());
    }

    @Override
    public Response delete ( String id ) {
        return new Response().setCode(200).setObject(scheduleTimeMapper.deleteById(id));
    }

    @Override
    @SneakyThrows(Exception.class)
    public Response insertExcel ( MultipartFile file, String season ) {
        log.info("文件名是：" + file.getOriginalFilename());
        ExcelReadListener excelReadListener = new ExcelReadListener(season);
        ExcelReader reader = EasyExcelFactory.read(file.getInputStream(), excelReadListener).build();
        // 读取Sheet,从第0行开始读取(表示从表头开始读)
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        reader.read(readSheet);
        reader.finish();
        List<String> head = excelReadListener.getHead();
        System.out.println(JSON.toJSONString(head));
        List<List<String>> data = excelReadListener.getData();
        System.out.println(JSON.toJSONString(data));
        return null;
    }

    @Override
    @SneakyThrows(Exception.class)
    public void getModelExcel ( String season, HttpServletResponse response ) {
        List<ScheduleTimeTab> scheduleTimeTabList = scheduleTimeMapper.selectList(new QueryWrapper<ScheduleTimeTab>().eq("season", season));
        response.setCharacterEncoding("utf8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(ExcelDictionary.FILE_NAME + ".xlsx", "UTF-8"));
        response.setHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "max-age=0");
        response.flushBuffer();
        List<List<String>> list = new ArrayList<>();
        list.add(Collections.singletonList(ExcelDictionary.TIME));
        if (Objects.nonNull(scheduleTimeTabList) && scheduleTimeTabList.size() > 0) {
            scheduleTimeTabList.forEach(scheduleTimeTab -> {
                List<String> heard = new ArrayList<>();
                heard.add(scheduleTimeTab.getSchedule());
                list.add(heard);
            });
        }
        log.info("表头参数是：" + JSON.toJSONString(list));
        EasyExcel.write(response.getOutputStream()).sheet(ExcelDictionary.TEMPLATE).head(list).doWrite(new ArrayList());
    }
}
