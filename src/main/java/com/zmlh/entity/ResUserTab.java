package com.zmlh.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * @ClassName ResUserTab
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 16:44
 * @Version 1.0
 **/

@Data
@Accessors(chain = true)
@TableName(value = "res_user_tab", schema = "zmyd")
public class ResUserTab {
    @TableId(value = "id")
    @NotNull
    @Length(min = 1, max = 64)
    private String id;

    @TableField(value = "roleid")
    private Integer roleId;

    @TableField(value = "username")
    @NotNull
    @Length(min = 1, max = 64)
    private String userName;

    @TableField(value = "name")
    @NotNull
    @Length(min = 1, max = 64)
    private String name;

    @TableField(value = "pwd")
    @NotNull
    @Length(min = 1, max = 128)
    private String pwd;

    @TableField(value = "createtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant createTime;

    @TableField(value = "updatetime", update = "now()", updateStrategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant updateTime;
}
