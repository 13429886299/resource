package com.zmlh.server.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zmlh.entity.DictAllInfo;
import com.zmlh.entity.DictInfo;
import com.zmlh.entity.Response;
import com.zmlh.mapper.DictAllInfoMapper;
import com.zmlh.mapper.DictInfoMapper;
import com.zmlh.server.DictServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName DictServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 16:57
 * @Version 1.0
 **/
@Service
@Slf4j
public class DictServerImpl implements DictServer {
    @Autowired
    private DictInfoMapper dictInfoMapper;
    @Autowired
    private DictAllInfoMapper dictAllInfoMapper;

    @Override
    public Response getAll () {
        List<DictInfo> dictInfoList = dictInfoMapper.selectList(null);
        List<DictAllInfo> dictAllInfoList = dictAllInfoMapper.selectList(null);
        dictInfoList.forEach(dictInfo -> dictInfo.getDictAllInfoList().addAll(
                dictAllInfoList.stream().filter(dictAllInfo -> dictAllInfo.getId().equals(dictInfo.getId()))
                        .collect(Collectors.toList()))
        );
        return new Response().setCode(200).setObject(dictInfoList);
    }

    @Override
    public Response insert ( DictInfo dictInfo ) {
        if (dictInfoMapper.selectById(dictInfo.getId()) == null) {
            return new Response().setCode(200).setObject(dictInfoMapper.insert(dictInfo));
        }
        return update(dictInfo);
    }

    @Override
    public Response insert ( DictAllInfo dictAllInfo ) {
        if (dictAllInfoMapper.selectById(dictAllInfo.getId()) == null) {
            List<DictInfo> dictInfoList = dictInfoMapper.selectList(null);
            boolean exit = dictInfoList.stream().anyMatch(dictInfo -> dictInfo.getId().equals(dictAllInfo.getId()));
            if (exit) {
                dictAllInfoMapper.insert(dictAllInfo);
                return new Response().setCode(200);
            }
            return new Response().setCode(500).setObject("不存在的字典信息");
        }
        return update(dictAllInfo);
    }

    @Override
    public Response update ( DictInfo dictInfo ) {
        dictInfoMapper.updateById(dictInfo);
        return new Response().setCode(200);
    }

    @Override
    public Response update ( DictAllInfo dictAllInfo ) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("code", dictAllInfo.getCode());
        updateWrapper.eq("id", dictAllInfo.getId());
        dictAllInfoMapper.update(dictAllInfo, updateWrapper);
        return new Response().setCode(200);
    }

    @Override
    public Response deleteBig ( String id ) {
        dictInfoMapper.deleteById(id);
        Map<String, Object> map = new HashMap<>(16);
        map.put("id", id);
        dictAllInfoMapper.deleteByMap(map);
        return new Response().setCode(200);
    }

    @Override
    public Response deleteSmall ( String id ) {
        dictAllInfoMapper.deleteById(id);
        return new Response().setCode(200);
    }

    @Override
    public Response getAllById ( String id ) {
        return null;
    }

    @Override
    public Response delete ( String id ) {
        return null;
    }
}
