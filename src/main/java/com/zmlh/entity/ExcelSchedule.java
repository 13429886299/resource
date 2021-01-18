package com.zmlh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

/**
 * @ClassName ExcelSchedule
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-15 15:42
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
@TableName(value = "res_excel_schedule_tab", schema = "zmyd")
public class ExcelSchedule {
    @TableId(value = "id")
    private String id;
    @TableField(value = "scheduleTime")
    private String scheduleTime;
    @TableField(value = "studentId")
    private String studentId;
    @TableField(value = "time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Instant time;
    @TableField(value = "type")
    private String type;
    @TableField(value = "`check`")
    private Integer check;
    @TableField(value = "season")
    private String season;
}
