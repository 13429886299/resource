package com.zmlh.server.impl;

import com.zmlh.entity.ResNodeTab;
import com.zmlh.entity.Response;
import com.zmlh.entity.ResultResource;
import com.zmlh.entity.StudentInfoTab;
import com.zmlh.mapper.ResNodeTabMapper;
import com.zmlh.mapper.StudentMapper;
import com.zmlh.server.ResourceServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ResourceServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 18:04
 * @Version 1.0
 **/
@Service
@Slf4j
public class ResourceServerImpl implements ResourceServer {
    @Autowired
    private ResNodeTabMapper resNodeTabMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Response getAll () {
        List<ResultResource> resultResourceList = new ArrayList<>();
        List<ResNodeTab> resNodeTabList = resNodeTabMapper.selectList(null);
        List<StudentInfoTab> studentInfoTabList = studentMapper.selectList(null);
        Map<String, StudentInfoTab> studentInfoTabMap = listToMap(studentInfoTabList);
        if (resNodeTabList.size() > 0) {
            resNodeTabList.forEach(resNodeTab -> {
                Map<String, Object> map = new HashMap<>();
                switch (resNodeTab.getType()) {
                    case "student":
                        student(map, studentInfoTabMap, resNodeTab.getId());
                        break;
                    case "class":
                        break;
                    default:
                        break;
                }
                resultResourceList.add(new ResultResource(resNodeTab.getId(), resNodeTab.getPid(), resNodeTab.getName(), resNodeTab.getType(), map));
            });
        }
        return new Response().setCode(200).setObject(resultResourceList);
    }

    @Override
    public Response getAllById ( String id ) {
        return null;
    }

    @Override
    public Response insert ( Object o ) {
        return null;
    }

    @Override
    public Response update ( Object o ) {
        return null;
    }

    @Override
    public Response delete ( String id ) {
        return null;
    }

    private Map<String, StudentInfoTab> listToMap ( List<StudentInfoTab> studentInfoTabList ) {
        Map<String, StudentInfoTab> map = new HashMap<>(16);
        studentInfoTabList.forEach(studentInfoTab -> map.put(studentInfoTab.getId(), studentInfoTab));
        return map;
    }


    private void student ( Map<String, Object> map, Map<String, StudentInfoTab> studentInfoTabMap, String id ) {
        StudentInfoTab studentInfoTab = studentInfoTabMap.get(id);
        if (studentInfoTab != null) {
            map.put("smallName", studentInfoTab.getSmallName());
            map.put("sex", studentInfoTab.getSex());
            map.put("phone", studentInfoTab.getPhone());
            map.put("school", studentInfoTab.getSchool());

            map.put("levelBig", studentInfoTab.getLevelBig());
            map.put("levelSmall", studentInfoTab.getLevelSmall());
            map.put("allHours", studentInfoTab.getAllHours());
            map.put("used", studentInfoTab.getUsed());
            map.put("remarks", studentInfoTab.getRemarks());
        }
    }


}
