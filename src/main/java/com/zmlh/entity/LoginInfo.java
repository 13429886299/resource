package com.zmlh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @ClassName LoginInfo
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-07 11:16
 * @Version 1.0
 **/
@Data
@TableName(value = "dict_login_tab", schema = "zmyd")
@Accessors(chain = true)
public class LoginInfo {
    @TableField(value = "id")
    private String id;
    @TableField(value = "name")
    @NotNull
    @Length(min = 1, max = 64)
    private String user;
    @TableField(value = "pwd")
    @NotNull
    @Length(min = 1, max = 64)
    private String pwd;
    @TableField(exist = false)
    private Integer roleId;
    @TableField(exist = false)
    private String userName;
}
