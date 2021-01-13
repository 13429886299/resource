package com.zmlh.server.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zmlh.dictionary.ExcelDictionary;
import com.zmlh.entity.ExcelFile;
import com.zmlh.entity.Response;
import com.zmlh.entity.ScheduleTimeTab;
import com.zmlh.mapper.ScheduleTimeMapper;
import com.zmlh.server.ScheduleTimeServer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public Response insertExcel ( MultipartFile file, String season ) {
        log.info("文件名是：" + file.getOriginalFilename());


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
        List<ExcelFile> excelFileList = new ArrayList<>();
        if (Objects.nonNull(scheduleTimeTabList) && scheduleTimeTabList.size() > 0) {
            ExcelFile excelFile = new ExcelFile().setTitle(ExcelDictionary.TEMPLATE);
            int lessonNum = scheduleTimeTabList.size();
            for (int i = 0; i < lessonNum; i++) {
                ExcelFile.class.getDeclaredField(ExcelDictionary.BASE_FILE + i).getAnnotation(ExcelProperty.class).value();
//                ExcelFile.class.getMethod(SET + ExcelDictionary.BASE_FILE + i, String.class).invoke(excelFile, scheduleTimeTabList.get(i).getSchedule());
            }
            excelFileList.add(excelFile);
        }
        log.info("写入Excel的数据是：" + JSON.toJSONString(excelFileList));
        EasyExcel.write(response.getOutputStream(), ExcelFile.class).sheet("").doWrite(excelFileList);
    }
}
