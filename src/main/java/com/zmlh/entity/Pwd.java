package com.zmlh.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @ClassName Pwd
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-12 15:39
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class Pwd {
    @NotNull
    private String user;
    @NotNull
    private String pwd;
}
