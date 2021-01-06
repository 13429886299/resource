package com.zmlh.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName Response
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 16:48
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class Response {
    private Integer code;
    private Object object;
}
