package com.zmlh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @ClassName DictAllInfo
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 17:09
 * @Version 1.0
 **/
@Data
@TableName(value = "dict_all_value_tab", schema = "zmyd")
@Accessors(chain = true)
public class DictAllInfo {
    @NotNull
    @TableId(value = "code", type = IdType.AUTO)
    private Integer code;
    @NotNull
    @Length(max = 64, min = 1)
    @TableField(value = "name")
    private String value;
    @NotNull
    @TableField(value = "id")
    private Integer id;
}
