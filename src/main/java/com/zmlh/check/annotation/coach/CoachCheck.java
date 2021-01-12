package com.zmlh.check.annotation.coach;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ @interface CoachCheck
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-11 18:49
 * @Version 1.0
 **/
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CoachCheckHandler.class})
public @interface CoachCheck {

    String message () default "不是教练账号,不能进行消课处理";

    // 分组
    Class<?>[] groups () default {};

    // 负载
    Class<? extends Payload>[] payload () default {};
}
