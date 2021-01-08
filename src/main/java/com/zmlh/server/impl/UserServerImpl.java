package com.zmlh.server.impl;

import com.zmlh.entity.ResUserTab;
import com.zmlh.entity.Response;
import com.zmlh.mapper.UserMapper;
import com.zmlh.server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @ClassName UserServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 16:52
 * @Version 1.0
 **/
@Service
public class UserServerImpl implements UserServer {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Response getAll () {
        return new Response().setCode(200).setObject(userMapper.selectList(null));
    }

    @Override
    public Response getAllById ( String id ) {
        return new Response().setCode(200).setObject(userMapper.selectById(id));
    }

    @Override
    public Response insert ( ResUserTab resUserTab ) {
        if (userMapper.selectById(resUserTab.getId()) == null) {
            resUserTab.setId(creatId());
            resUserTab.setCreateTime(Instant.now()).setUpdateTime(Instant.now());
            return new Response().setCode(200).setObject(userMapper.insert(resUserTab));
        }
        return update(resUserTab);
    }

    @Override
    public Response update ( ResUserTab resUserTab ) {
        return new Response().setCode(200).setObject(userMapper.updateById(resUserTab));
    }

    @Override
    public Response delete ( String id ) {
        return new Response().setCode(200).setObject(userMapper.deleteById(id));
    }
}
