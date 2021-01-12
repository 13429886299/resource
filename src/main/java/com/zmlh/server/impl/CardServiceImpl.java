package com.zmlh.server.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zmlh.entity.ResRecordLessonTab;
import com.zmlh.entity.Response;
import com.zmlh.entity.StudentInfoTab;
import com.zmlh.mapper.StudentMapper;
import com.zmlh.server.CardService;
import com.zmlh.server.RecordLessonServer;
import jcumf.umf_javacall;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @ClassName DictServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 16:57
 * @Version 1.0
 **/
@Service
@Slf4j
public class CardServiceImpl implements CardService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private RecordLessonServer recordLessonServer;

    @Override
    public Response editStudentLessons ( String cardId, String coachId, Instant time ) {
        log.info("开始扣除学生剩余课程");
        Response response = new Response();
        QueryWrapper<StudentInfoTab> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cardId", cardId);
        StudentInfoTab studentInfo = studentMapper.selectOne(queryWrapper);
        if (studentInfo != null) {
            response.setCode(200);
            int used = studentInfo.getUsed() + 1;
            int remain = studentInfo.getAllHours() - studentInfo.getUsed() - 1;
            if (remain >= 0) {
                UpdateWrapper<StudentInfoTab> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("cardId", cardId);
                studentMapper.update(new StudentInfoTab().setUsed(used).setRemain(remain), updateWrapper);
                response.setObject("扣除课程成功，还剩" + remain + "节课");
                recordLessonServer.insert(new ResRecordLessonTab()
                        .setLessonType(studentInfo.getLevelSmall())
                        .setCoachId(coachId)
                        .setStudentId(studentInfo.getId())
                        .setCustomerTime(time)
                );
            } else {
                response.setObject("对不起，你的课程已经用完了");
                log.info(studentInfo.getUserName() + "的课程已经用完了");
            }
        }
        return response;
    }

    @Override
    public Response getCardId () {
        Response response = new Response();
        short status, findCardMode = 1;
        int lDevice = 0;
        char[] pSnrM1 = new char[8];
        lDevice = umf_javacall.fw_init(100, 115200);
        if (lDevice <= 0) {
            log.info("读卡器识别错误");
            return response.setCode(500).setObject("读卡器识别错误或者没插好");
        }
        status = umf_javacall.fw_card_hex(lDevice, findCardMode, pSnrM1);
        if (status != 0) {
            log.info("卡片识别错误，读取失败，状态值：" + status);
            response.setObject("请放好卡片或卡片已经损坏");
        } else {
            log.info("读卡成功，卡片值是：" + JSON.toJSONString(pSnrM1));
            response.setCode(200).setObject(JSON.toJSONString(pSnrM1));
        }
        status = umf_javacall.fw_exit(lDevice);
        if (status != 0) {
            log.info("读卡退出失败，状态是：" + status);
        } else {
            log.info("读卡退出成功" + status);
        }
        return response;
    }
}
