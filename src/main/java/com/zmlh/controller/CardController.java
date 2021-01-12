package com.zmlh.controller;


import com.alibaba.fastjson.JSON;
import com.zmlh.entity.Lesson;
import com.zmlh.entity.Response;
import com.zmlh.server.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName DictServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 16:57
 * @Version 1.0
 **/

@RestController
@RequestMapping("/zmlh/card")
@Slf4j
public class CardController {

    @Autowired
    private CardService cardService;

    @RequestMapping(value = "/lesson", method = RequestMethod.POST)
    public Response editStudentLessons (  @Validated @RequestBody Lesson lesson ) {
        log.info("需要扣除和记录的信息是：" + JSON.toJSONString(lesson));
        return cardService.editStudentLessons(lesson.getCardId(), lesson.getCoachId(), lesson.getTime());
    }

    @RequestMapping(value = "/cardId", method = RequestMethod.GET)
    public Response getCardId () {
        log.info("开始获取学生卡信息");
        return cardService.getCardId();
    }
}
