package com.zmlh.server.impl;

import com.zmlh.entity.ResRoleTab;
import com.zmlh.entity.Response;
import com.zmlh.mapper.RoleMapper;
import com.zmlh.server.RoleServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName RoleServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 11:08
 * @Version 1.0
 **/
@Service
public class RoleServerImpl implements RoleServer {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Response getAll () {
        return new Response().setCode(200).setObject(roleMapper.selectList(null));
    }

    @Override
    public Response getAllById ( String id ) {
        return new Response().setCode(200).setObject(roleMapper.selectById(id));
    }

    @Override
    public Response insert ( ResRoleTab resRoleTab ) {
        return insert(roleMapper, resRoleTab, resRoleTab.getId());
    }

    @Override
    public Response update ( ResRoleTab resRoleTab ) {
        return new Response().setCode(200).setObject(roleMapper.updateById(resRoleTab));
    }

    @Override
    public Response delete ( String id ) {
        return new Response().setCode(200).setObject(roleMapper.deleteById(id));
    }
}
