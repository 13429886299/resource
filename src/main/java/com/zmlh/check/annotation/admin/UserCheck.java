package com.zmlh.check.annotation.admin;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Interface UserCheck
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-12 11:39
 * @Version 1.0
 **/
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UserCheckHandler.class})
public @interface UserCheck {
    String message () default "该账户没有权限进行此项操作";

    // 分组
    Class<?>[] groups () default {};

    // 负载
    Class<? extends Payload>[] payload () default {};
}
