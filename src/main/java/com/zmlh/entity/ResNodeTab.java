package com.zmlh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @ClassName ResNodeTabMapper
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 20:08
 * @Version 1.0
 **/
@Data
@TableName(value = "res_node_tab", schema = "zmyd")
@Accessors(chain = true)
public class ResNodeTab {
    @TableId(value = "id")
    @NotNull
    private String id;
    @TableField(value = "pid")
    @NotNull
    private String pid;
    @TableField(value = "type")
    @NotNull
    private String type;
    @TableField(value = "name")
    @NotNull
    @Length(min = 1, max = 64)
    private String name;
}
