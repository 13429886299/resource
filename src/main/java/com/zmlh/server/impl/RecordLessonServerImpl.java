package com.zmlh.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zmlh.entity.*;
import com.zmlh.mapper.DictAllInfoMapper;
import com.zmlh.mapper.RecordLessonMapper;
import com.zmlh.mapper.StudentMapper;
import com.zmlh.mapper.UserMapper;
import com.zmlh.server.RecordLessonServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName RecordLessonServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-08 15:02
 * @Version 1.0
 **/
@Service
@Slf4j
public class RecordLessonServerImpl implements RecordLessonServer {
    @Autowired
    private RecordLessonMapper recordLessonMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private DictAllInfoMapper dictAllInfoMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public Response getAll () {

        return new Response().setCode(200).setObject(get());
    }

    private List<ResRecordLessonTab> get () {
        List<ResRecordLessonTab> resRecordLessonTabList = recordLessonMapper.selectList(null);
        List<StudentInfoTab> studentInfoTabList = studentMapper.selectList(null);
        Map<String, StudentInfoTab> studentMap = listToMap(studentInfoTabList, ID);
        List<ResUserTab> userList = userMapper.selectList(null);
        Map<String, ResUserTab> userMap = listToMap(userList, ID);
        QueryWrapper<DictAllInfo> query = new QueryWrapper<>();
        query.eq(ID, 2);
        List<DictAllInfo> dictList = dictAllInfoMapper.selectList(query);
        resRecordLessonTabList.forEach(resRecordLessonTab ->
                resRecordLessonTab.setStudentName(getS(studentMap, resRecordLessonTab.getStudentId(), StudentInfoTab.class).getUserName())
                        .setCoachName(getS(userMap, resRecordLessonTab.getCoachId(), ResUserTab.class).getUserName())
                        .setLessonName(
                                dictList.stream()
                                        .filter(dict -> dict.getCode().equals(resRecordLessonTab.getLessonType()))
                                        .findFirst().orElse(new DictAllInfo())
                                        .getValue()
                        )
        );
        return resRecordLessonTabList;
    }

    @Override
    public Response getAllById ( String id ) {
        return new Response().setCode(200).setObject(
                get().stream()
                        .filter(resRecordLessonTab -> resRecordLessonTab.getStudentId().equals(id))
                        .findAny()
                        .orElse(new ResRecordLessonTab())
        );
    }

    @Override
    public Response insert ( ResRecordLessonTab resRecordLessonTab ) {
        return null;
    }

    @Override
    public Response update ( ResRecordLessonTab resRecordLessonTab ) {
        return null;
    }

    @Override
    public Response delete ( String id ) {
        return null;
    }
}
