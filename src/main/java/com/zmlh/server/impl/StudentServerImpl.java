package com.zmlh.server.impl;

import com.zmlh.entity.ResNodeTab;
import com.zmlh.entity.Response;
import com.zmlh.entity.StudentInfoTab;
import com.zmlh.mapper.ResNodeTabMapper;
import com.zmlh.mapper.StudentMapper;
import com.zmlh.server.StudentServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName StudentServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-12 19:27
 * @Version 1.0
 **/
@Service
@Slf4j
public class StudentServerImpl implements StudentServer {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ResNodeTabMapper resNodeTabMapper;

    @Override
    public Response getAll () {
        return null;
    }

    @Override
    public Response getAllById ( String id ) {
        return null;
    }

    @Override
    public Response insert ( StudentInfoTab studentInfo ) {
        Response response = insert(studentMapper, studentInfo, studentInfo.getId());
        resNodeTabMapper.insert(new ResNodeTab()
                .setId(response.getObject().toString())
                .setName(studentInfo.getUserName())
                .setPid(studentInfo.getPid())
                .setType("student")
        );
        return response;
    }

    @Override
    public Response update ( StudentInfoTab studentInfo ) {
        studentMapper.updateById(studentInfo);
        resNodeTabMapper.updateById(new ResNodeTab()
                .setId(studentInfo.getId())
                .setName(studentInfo.getUserName())
                .setPid(studentInfo.getPid())
                .setType("student")
        );
        return new Response().setCode(200).setObject(studentInfo.getId());
    }

    @Override
    public Response delete ( String id ) {
        resNodeTabMapper.deleteById(id);
        studentMapper.deleteById(id);
        return new Response().setCode(200);
    }
}
