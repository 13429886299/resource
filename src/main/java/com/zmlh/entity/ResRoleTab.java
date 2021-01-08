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
 * @ClassName ResRoleTab
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 10:29
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
@TableName(value = "res_role_tab", schema = "zmyd")
public class ResRoleTab {
    @TableId(value = "id")
    @NotNull
    private String id;

    @TableField(value = "rolename")
    @NotNull
    @Length(max = 64, min = 1)
    private String roleName;

    @TableField(value = "roletype")
    @NotNull
    private Integer roleType;

    /**
     * 用于显示菜单栏
     */

    @TableField(value = "level")
    @NotNull
    private Integer level;


    @TableField(value = "createtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant createTime;

    @TableField(value = "updatetime", update = "now()", updateStrategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant updateTime;
}
