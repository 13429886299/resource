package com.zmlh.server.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zmlh.dictionary.ExcelDictionary;
import com.zmlh.entity.*;
import com.zmlh.mapper.DictAllInfoMapper;
import com.zmlh.mapper.ScheduleTimeMapper;
import com.zmlh.mapper.StudentMapper;
import com.zmlh.server.ScheduleTimeServer;
import com.zmlh.until.ExcelReadListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

/**
 * @ClassName ExcelScheduleServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-13 15:25
 * @Version 1.0
 **/
@Service
@Slf4j
public class ExcelScheduleServerImpl implements ScheduleTimeServer {
    @Autowired
    private ScheduleTimeMapper scheduleTimeMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private DictAllInfoMapper dictAllInfoMapper;
    @Autowired
    private AbstractExcelScheduleServer abstractExcelScheduleServer;

    @Override
    public Response getAll () {
        List<ExcelSchedule> excelScheduleList = abstractExcelScheduleServer.list();
        setValue(excelScheduleList);
        return new Response().setCode(200).setObject(excelScheduleList);
    }


    @Override
    public Response getPage ( int pageNo, int pageSize ) {
        IPage<ExcelSchedule> excelSchedulePage = new Page<>(pageNo, pageSize);
        IPage<ExcelSchedule> excelScheduleListPage = abstractExcelScheduleServer.getBaseMapper().selectPage(excelSchedulePage, new QueryWrapper<>());
        List<ExcelSchedule> excelScheduleList = new ArrayList<>();
        if (Objects.nonNull(excelScheduleListPage)) {
            excelScheduleList = excelScheduleListPage.getRecords();
        }
        setValue(excelScheduleList);
        excelScheduleListPage.setRecords(excelScheduleList);
        return new Response().setCode(200).setObject(excelScheduleListPage);
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
    public Response insertExcel ( MultipartFile file, String season, Instant instant ) {
        log.info("文件名是：" + file.getOriginalFilename());
        List<ScheduleTimeTab> scheduleTimeTabList = scheduleTimeMapper.selectList(new QueryWrapper<ScheduleTimeTab>().eq("season", season));
        List<StudentInfoTab> studentInfoTabList = studentMapper.selectList(new QueryWrapper<>());
        List<DictAllInfo> dictAllInfoList = dictAllInfoMapper.selectList(new QueryWrapper<DictAllInfo>().eq("id", 2));
        ExcelReadListener excelReadListener = new ExcelReadListener(scheduleTimeTabList, studentInfoTabList);
        ExcelReader reader = EasyExcelFactory.read(file.getInputStream(), excelReadListener).build();
        // 读取Sheet,从第0行开始读取(表示从表头开始读)
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        reader.read(readSheet).finish();
        Map<String, List<String>> listMap = excelReadListener.getMapList();
        log.info("获取到的Excel文件中的数据是：" + JSON.toJSONString(listMap));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        String time = simpleDateFormat.format(Date.from(instant));
        List<ExcelSchedule> excelScheduleList = new ArrayList<>();
        abstractExcelScheduleServer.getBaseMapper().delete(new UpdateWrapper<ExcelSchedule>().eq("season", season).eq("time", time));
        listMap.forEach(( key, list ) ->
                list.forEach(s -> {
                    int length = list.size() - 1;
                    if (list.indexOf(s) < length) {
                        excelScheduleList.add(new ExcelSchedule()
                                .setId(creatId())
                                .setSeason(season)
                                .setTime(instant)
                                .setScheduleTime(key)
                                .setCheck(0)
                                .setStudentId(studentInfoTabList.stream()
                                        .filter(studentInfo -> studentInfo.getUserName().equals(s))
                                        .findFirst()
                                        .orElse(new StudentInfoTab().setId(""))
                                        .getId()
                                )
                                .setType(dictAllInfoList.stream()
                                        .filter(dictAllInfo -> dictAllInfo.getValue().equals(list.get(length)))
                                        .findAny()
                                        .orElse(new DictAllInfo().setCode(-1))
                                        .getCode()
                                        .toString()
                                )
                        );
                    }
                })
        );
        abstractExcelScheduleServer.saveBatch(excelScheduleList);
        log.info("需要保存的课表信息是：" + JSON.toJSONString(excelScheduleList));
        return new Response().setCode(200);
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

    private void setValue ( List<ExcelSchedule> excelScheduleList ) {
        List<StudentInfoTab> studentInfoTabList = studentMapper.selectList(new QueryWrapper<>());
        List<DictAllInfo> dictAllInfoList = dictAllInfoMapper.selectList(new QueryWrapper<DictAllInfo>().eq("id", 2));
        excelScheduleList.forEach(excelSchedule ->
                excelSchedule.setStudentName(studentInfoTabList.stream()
                        .filter(studentInfoTab -> studentInfoTab.getId().equals(excelSchedule.getStudentId()))
                        .findAny().orElse(new StudentInfoTab().setUserName(StringUtils.EMPTY))
                        .getUserName()
                )
                        .setCheckName()
                        .setTypeName(dictAllInfoList.stream()
                                .filter(dictAllInfo -> dictAllInfo.getCode().toString().equals(excelSchedule.getType()))
                                .findAny()
                                .orElse(new DictAllInfo().setValue(StringUtils.EMPTY))
                                .getValue()
                        )
        );
    }
}
