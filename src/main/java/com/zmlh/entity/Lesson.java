package com.zmlh.entity;

import com.zmlh.check.annotation.coach.CoachCheck;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

/**
 * @ClassName Lesson
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-11 19:35
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class Lesson {
    private String cardId;
    @CoachCheck
    private String coachId;
    private Instant time;
}
