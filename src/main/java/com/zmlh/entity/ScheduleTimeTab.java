package com.zmlh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @ClassName ScheduleTimeTab
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-13 15:20
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
@TableName(value = "res_schedule_time_tab", schema = "zmyd")
public class ScheduleTimeTab {
    @TableId(value = "id")
    private String id;
    @TableField(value = "schedule")
    @NotNull
    private String schedule;
    @TableField(value = "season")
    private Integer season;
}
