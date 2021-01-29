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
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private final static String TIME_FORMAT = "yyyy-MM-dd";

    @Override
    public Response getAll () {
        List<ScheduleTimeTab> excelScheduleTimeList = scheduleTimeMapper.selectList(new QueryWrapper<>());
        return new Response().setCode(200).setObject(excelScheduleTimeList);
    }


    @Override
    public Response getPage ( int pageNo, int pageSize, Instant instant ) {
        IPage<ExcelSchedule> excelSchedulePage = new Page<>(pageNo, pageSize);
        String time = getStrTime(instant, TIME_FORMAT);
        log.info("time:" + time);
        IPage<ExcelSchedule> excelScheduleListPage = abstractExcelScheduleServer.getBaseMapper()
                .selectPage(excelSchedulePage, new QueryWrapper<ExcelSchedule>().eq("time", time));
        List<ExcelSchedule> excelScheduleList = new ArrayList<>();
        if (Objects.nonNull(excelScheduleListPage)) {
            excelScheduleList = excelScheduleListPage.getRecords();
        }
        setValue(excelScheduleList);
        excelScheduleListPage.setRecords(excelScheduleList);
        log.info("excelScheduleListPage:" + JSON.toJSONString(excelScheduleListPage));
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
    public Response insertExcel ( MultipartFile file, ZonedDateTime instant ) {
        log.info("文件名是：" + file.getOriginalFilename());
        String season = getSeason(LocalDate.ofEpochDay(instant.toEpochSecond())) + "";
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
        List<ExcelSchedule> excelScheduleList = new ArrayList<>();
        String time = getStrTime(instant.toInstant(), TIME_FORMAT);
        log.info("time:" + time);
        abstractExcelScheduleServer.getBaseMapper().delete(new UpdateWrapper<ExcelSchedule>().eq("season", season).eq("time", time));
        listMap.forEach(( key, list ) ->
                list.forEach(s -> {
                    int length = list.size() - 1;
                    if (list.indexOf(s) < length) {
                        excelScheduleList.add(new ExcelSchedule()
                                .setId(creatId())
                                .setSeason(season)
                                .setTime(instant.toInstant())
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
    public void getModelExcel ( long time, HttpServletResponse response ) {
        LocalDate localDate = LocalDate.ofEpochDay(new Date(time).toInstant().getEpochSecond());
        log.info("时间日期是：" + localDate.toString());
        List<ScheduleTimeTab> scheduleTimeTabList = scheduleTimeMapper.selectList(new QueryWrapper<ScheduleTimeTab>().eq("season", getSeason(localDate)));
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

    private String getStrTime ( Instant instant, String format ) {
        log.info("instant:" + instant.toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(Date.from(instant));
    }

    private int getSeason ( LocalDate date ) {
        int month = date.getMonthValue();
        int season = (int) Math.ceil(month / 3.0);
        log.info("当前季节是：" + season);
        return season;
    }


}
