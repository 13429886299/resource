package com.zmlh.entity;

/**
 * @ClassName ResRecordLessonTab
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-08 14:25
 * @Version 1.0
 **/

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@TableName(value = "res_record_lesson_tab", schema = "zmyd")
@Accessors(chain = true)
public class ResRecordLessonTab {
    @TableId(value = "id")
    @NotNull
    private String id;

    @TableField(value = "studentid")
    @NotNull
    @Length(max = 64, min = 1)
    private String studentId;

    @TableField(exist = false)
    @Length(max = 64, min = 1)
    private String studentName;

    @TableField(value = "coachid")
    @NotNull
    @Length(max = 64, min = 1)
    private String coachId;

    @TableField(exist = false)
    @Length(max = 64, min = 1)
    private String coachName;

    @TableField(value = "lessontype")
    @NotNull
    @Length(max = 64, min = 1)
    private Integer lessonType;

    @TableField(exist = false)
    @Length(max = 64, min = 1)
    private String lessonName;

    @TableField(value = "createtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant createTime;

    @TableField(value = "customertime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant customerTime;


}
