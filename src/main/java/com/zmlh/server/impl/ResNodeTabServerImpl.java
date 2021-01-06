package com.zmlh.server.impl;

import com.zmlh.entity.ResNodeTab;
import com.zmlh.entity.Response;
import com.zmlh.mapper.ResNodeTabMapper;
import com.zmlh.server.ResNodeTabServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @ClassName ResNodeTabServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 20:12
 * @Version 1.0
 **/
@Service
@Slf4j
public class ResNodeTabServerImpl implements ResNodeTabServer {
    @Autowired
    private ResNodeTabMapper resNodeTabMapper;

    @Override
    public Response getAll () {
        return new Response().setCode(200).setObject(resNodeTabMapper.selectList(null));
    }

    @Override
    public Response getAllById ( String id ) {
        return new Response().setCode(200).setObject(resNodeTabMapper.selectById(id));
    }

    @Override
    public Response insert ( ResNodeTab resNodeTab ) {
        if (resNodeTabMapper.selectById(resNodeTab.getId()) == null) {
            resNodeTab.setId(creatId());
            return new Response().setCode(200).setObject(resNodeTabMapper.insert(resNodeTab));
        }
        return update(resNodeTab);

    }

    @Override
    public Response update ( ResNodeTab resNodeTab ) {
        return new Response().setCode(200).setObject(resNodeTabMapper.updateById(resNodeTab));
    }

    @Override
    public Response delete ( String id ) {
        return new Response().setCode(200).setObject(resNodeTabMapper.deleteById(id));
    }
}
