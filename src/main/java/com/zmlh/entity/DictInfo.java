package com.zmlh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DictInfo
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 16:51
 * @Version 1.0
 **/
@Data
@TableName(value = "dict_all_tab", schema = "zmyd")
@Accessors(chain = true)
public class DictInfo {
    @TableId(value = "id")
    @NotNull
    private Integer id;
    @TableField(value = "name")
    @Length(max = 64, min = 1)
    private String name;
    @TableField(exist = false)
    private List<DictAllInfo> dictAllInfoList = new ArrayList<>();
}
