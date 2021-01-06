package com.zmlh.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * @ClassName StudentInfoTab
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 16:55
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
@TableName(value = "student_info_tab", schema = "zmyd")
public class StudentInfoTab {
    @TableId(value = "id")
    @Length(min = 1, max = 64)
    private String id;

    @TableField(value = "pid")
    @NotNull
    @Length(min = 1, max = 64)
    private String pid;

    @TableField(value = "user_name")
    @Length(min = 1, max = 64)
    @NotNull
    private String userName;

    @TableField(value = "small_name")
    @Length(min = 1, max = 64)
    private String smallName;

    @TableField(value = "sex")
    @NotNull
    @Length(min = 1, max = 64)
    private String sex;

    @TableField(value = "num")
    @Length(min = 1, max = 64)
    private String num;

    @TableField(value = "school")
    @Length(min = 1, max = 64)
    private String school;

    @TableField(value = "phone")
    @Length(min = 1, max = 13)
    private String phone;

    @TableField(value = "level_big")
    private Integer levelBig;

    @TableField(value = "level_small")
    private Integer levelSmall;

    @TableField(value = "all_hours")
    private Integer allHours;

    @TableField(value = "used")
    private Integer used;

    @TableField(value = "remain")
    private Integer remain;

    @TableField(value = "create_time")
    private Instant createTime;

    @TableField(value = "update_time")
    private Instant updateTime;

    @TableField(value = "remarks")
    private String remarks;

    @TableField(value = "startTime")
    private Instant startTime;

    @TableField(value = "endTime")
    private Instant endTime;

    @TableField(value = "age")
    private Integer age;

    @TableField(value = "cardId")
    @NotNull
    @Length(min = 1, max = 64)
    private String cardId;
}
