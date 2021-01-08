package com.zmlh.controller;


import com.zmlh.entity.Response;
import com.zmlh.server.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DictServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 16:57
 * @Version 1.0
 **/

@RestController
@RequestMapping("/zmlh/cardId")
@Slf4j
public class CardController {

    @Autowired
    CardService cardService;

    @RequestMapping(value = "/{cardId}", method = RequestMethod.GET)
    public Response editStudentLessons ( @PathVariable String cardId ) {
        log.info("收取到的会员卡号是：" + cardId);
        return cardService.editStudentLessons(cardId);
    }

    @RequestMapping(value = "/card", method = RequestMethod.GET)
    public Response getCardId () {
        log.info("开始获取学生卡信息");
        return cardService.getCardId();
    }

}
